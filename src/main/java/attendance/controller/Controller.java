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
import java.time.LocalDate;

public class Controller {
    public static final String FILE_NAME = "attendances.csv";
    private static final String NOT_ATTENDABLE_FORMAT_STRING = "MM월 dd일 EEEE은 등교일이 아닙니다.";

    private final InputView inputView;
    private final OutputView outputView;
    private static final Crews crews = new Crews();

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void loadDate() {
        CrewDataLoader crewDataLoader = new CrewDataLoader(crews, CustomLocalDateTime.now());
        crewDataLoader.load(FILE_NAME);
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

    private void processAddAttendance() {
        process(() -> {
            if (CustomLocalDateTime.isTodayHoliday()) {
                throw new IllegalArgumentException(CustomLocalDateTime.formatNowDateTime(NOT_ATTENDABLE_FORMAT_STRING));
            }
            Crew crew = crews.findCrew(inputView.inputCrewName());
            AttendanceDetail attendanceDetail = new AttendanceDetail(
                    CustomLocalDateTime.generateAttendanceDateTime(inputView.inputEntryTime())
            );
            crew.attend(attendanceDetail);
            outputView.printAttendanceDetail(AttendanceDetailDTO.from(attendanceDetail));
        });
    }

    private void processModifyAttendance() {
        process(() -> {
            AttendanceDetail attendanceDetail = crews.getCrewAttendanceDetail(
                    inputView.inputModifyAttendanceCrewName(),
                    LocalDate.of(2024, 12, Parser.parseInt(inputView.inputModifyAttendanceDate()))
            );
            AttendanceDetail beforeModify = new AttendanceDetail(attendanceDetail.getAttendanceDateTime());
            attendanceDetail.modify(CustomLocalDateTime.parseTime(inputView.inputModifyAttendanceTime()));
            outputView.printModifyResult(
                    AttendanceDetailDTO.from(beforeModify),
                    AttendanceDetailDTO.from(attendanceDetail)
            );
        });
    }

    private void processDisplayAttendanceHistory() {
        process(() -> outputView.printAttendanceHistory(
                AttendanceDTO.from(crews.findCrew(inputView.inputCrewName())))
        );
    }

    private void processDisplayWarningCrew() {
        process(() -> outputView.printWarningCrews(WarningCrewsDTO.from(crews)));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
