package attendance.controller;

import attendance.dto.AttendanceDTO;
import attendance.dto.AttendanceDTO.AttendanceDetailDTO;
import attendance.dto.WarningCrewsDTO;
import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import attendance.model.CrewDataLoader;
import attendance.model.Crews;
import attendance.model.CustomLocalDateTime;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final static Crews crews = new Crews();

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void loadDate() {
        CrewDataLoader crewDataLoader = new CrewDataLoader(crews, CustomLocalDateTime.now());
        crewDataLoader.load("attendances.csv");
    }

    public void run() {
        String s = inputView.inputCommand();
        if (s.equals("1")) {
            processAddAttendance();
        }
        if (s.equals("2")) {
            processModifyAttendance();
        }
        if (s.equals("3")) {
            processDisplayAttendanceHistory();
        }
        if (s.equals(("4"))) {
            processDisplayWarningCrew();
        }
        if (s.equals("Q")) {
            System.exit(1);
        }
        run();
    }

    private void processDisplayWarningCrew() {
        process(() -> {
            outputView.printWarningCrews(WarningCrewsDTO.from(crews));
        });
    }

    private void processDisplayAttendanceHistory() {
        process(() -> {
            Crew crew = crews.findCrew(inputView.inputCrewName());
            outputView.printAttendanceHistory(AttendanceDTO.from(crew));
        });
    }

    private void processModifyAttendance() {
        process(() -> {
            Crew crew = crews.findCrew(inputView.inputModifyAttendanceCrewName());
            AttendanceDetail attendanceDetail = crew.findAttendanceDetail(
                    CustomLocalDateTime.parseDate(inputView.inputModifyAttendanceDate())
            );
            AttendanceDetail beforeModify = new AttendanceDetail(attendanceDetail.getAttendanceDateTime());
            attendanceDetail.modify(CustomLocalDateTime.parseTime(inputView.inputModifyAttendanceTime()));
            outputView.printModifyResult(
                    AttendanceDetailDTO.from(beforeModify),
                    AttendanceDetailDTO.from(attendanceDetail)
            );
        });
    }

    private void processAddAttendance() {
        process(() -> {
            Crew crew = crews.findCrew(inputView.inputCrewName());
            AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(
                    CustomLocalDateTime.nowDate(),
                    CustomLocalDateTime.parseTime(inputView.inputEntryTime())
            ));
            crew.attend(attendanceDetail);
            outputView.printAttendanceDetail(AttendanceDetailDTO.from(attendanceDetail));
        });
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
