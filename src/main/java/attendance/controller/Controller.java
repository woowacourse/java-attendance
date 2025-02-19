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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
            String crewName = inputView.inputCrewName();
            String entryTime = inputView.inputEntryTime();
            AttendanceDetail attendanceDetail = new AttendanceDetail(
                    LocalDateTime.of(CustomLocalDateTime.now().toLocalDate(),
                            LocalTime.parse(entryTime, DateTimeFormatter.ofPattern("HH:mm")))
            );
            crews.findCrew(new Crew(crewName)).get().getAttendanceHistory().addAttendanceDetail(attendanceDetail);
            outputView.printAttendanceDetail(AttendanceDetailDTO.from(attendanceDetail));
        }
        if (s.equals("2")) {
            String crewName = inputView.inputModifyAttendanceCrewName();
            String modifyDateInput = inputView.inputModifyAttendanceDate();
            LocalDate modifyDate = LocalDate.of(2024, 12, Integer.parseInt(modifyDateInput));
            String modifyTimeInput = inputView.inputModifyAttendanceTime();
            LocalTime modifyTime = LocalTime.parse(modifyTimeInput, DateTimeFormatter.ofPattern("HH:mm"));

            AttendanceDetail attendanceDetail = crews.findCrew(new Crew(crewName)).get().getAttendanceHistory()
                    .getAttendanceDetail(modifyDate);

            AttendanceDetail cloned = attendanceDetail.clone();
            attendanceDetail.modify(modifyTime);

            outputView.printModifyResult(
                    AttendanceDetailDTO.from(cloned),
                    AttendanceDetailDTO.from(attendanceDetail)
            );
        }
        if (s.equals("3")) {
            String crewName = inputView.inputCrewName();
            Crew crew = crews.findCrew(new Crew(crewName)).get();
            outputView.printAttendanceHistory(AttendanceDTO.from(crew));
        }
        if (s.equals(("4"))) {
            outputView.printWarningCrews(WarningCrewsDTO.from(crews));
        }
        if (s.equals("Q")) {
            System.exit(1);
        }
        run();
    }
}
