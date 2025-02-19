package attendance.controller;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceResult;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.CrewManager;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {
    private final InputView inputView;
    private final CrewManager crewManager;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, CrewManager crewManager, OutputView outputView) {
        this.inputView = inputView;
        this.crewManager = crewManager;
        this.outputView = outputView;
    }

    public void start() {
        LocalDate now = LocalDate.now();
        String option = inputView.inputOption(now);
        if (option.equals("1")) {
            AttendanceManager.checkHoliday(now);
            String crewName = inputView.inputCrewName();
            Crew crew = crewManager.findByCrewName(crewName);
            LocalTime attendanceTime = inputView.inputAttendanceTime();
            AttendanceType attendanceType = AttendanceManager.checkAttendanceType(now.getDayOfWeek(), attendanceTime);
            AttendanceResult attendanceResult = new AttendanceResult(LocalDateTime.of(now, attendanceTime), attendanceType);
            crew.addAttendanceResult(attendanceResult);
            outputView.printAttendanceResult(attendanceResult);
            return;
        }
        if (option.equals("2")) {
            return;
        }
        if (option.equals("3")) {
            return;
        }
        if (option.equals("4")) {
            return;
        }
        if (option.equals("Q")) {
            return;
        }
        throw new IllegalArgumentException("잘못된 입력 입니다.");
    }
}
