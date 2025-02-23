package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendance;
import attendance.model.AttendanceResult;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.model.Crew;
import attendance.model.MonthlyAttendance;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Attendances attendances;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        attendances = new AttendancesFactory().initialize();
    }

    public void run() {
        boolean isQuit;
        do {
            LocalDateTime now = LocalDateTime.of(2024, 12, 13, 18, 0);
            isQuit = start(now);
        } while (isQuit);
    }

    private boolean start(LocalDateTime now) {
        Command command = null;
        try {
            command = Command.from(inputView.inputCommand(now.toLocalDate()));
            startCommand(command, now);
        } catch (DateTimeException e) {
            outputView.printDateTimeErrorMessage();
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        return command != Command.QUIT;
    }

    private void startCommand(Command command, LocalDateTime now) {
        if (command == Command.ATTENDANCE) {
            doAttendance(now);
        }
        if (command == Command.ATTENDANCE_UPDATE) {
            doUpdateAttendance(now);
        }
        if (command == Command.ATTENDANCE_TIMELINE) {
            doAttendanceTimeline(now);
        }
        if (command == Command.EMERGENCY_CHECK) {
            doEmergencyCheck(now);
        }
    }

    private void doAttendance(LocalDateTime now) {
        Attendance attendance = createAttendance(now);
        attendances.add(attendance);
        outputView.printCheckAttendance(attendance);
    }

    private Attendance createAttendance(LocalDateTime now) {
        Crew crew = createCrew();
        LocalDateTime attendanceDateTime = inputAttendanceDateTime(now);
        return new Attendance(crew, attendanceDateTime);
    }

    private LocalDateTime inputAttendanceDateTime(LocalDateTime now) {
        String rawAttendanceTime = inputView.inputAttendanceTime();
        LocalTime attendanceTime = toLocalTime(rawAttendanceTime);
        return LocalDateTime.of(now.toLocalDate(), attendanceTime);
    }

    private void doUpdateAttendance(LocalDateTime now) {
        Crew crew = new Crew(inputExistNicknameForUpdate());
        LocalDateTime updateDateTime = createUpdateDateTime(now);
        Attendance beforeAttendance = attendances.findByCrewAndDate(crew, updateDateTime.toLocalDate());
        Attendance modifidedAttendance = attendances.update(now.toLocalDate(), new Attendance(crew, updateDateTime));
        outputView.printModifiedAttendance(beforeAttendance, modifidedAttendance);
    }

    private String inputExistNicknameForUpdate() {
        String nickname = inputView.inputNicknameForUpdateAttendance();
        attendances.validateExistNickname(nickname);
        return nickname;
    }

    private LocalDateTime createUpdateDateTime(LocalDateTime now) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        String rawTimeForUpdate = inputView.inputTimeForUpdateAttendance();

        LocalDate updateDate = LocalDate.of(now.getYear(), now.getMonth(), targetUpdateDate);
        return LocalDateTime.of(updateDate, toLocalTime(rawTimeForUpdate));
    }

    private LocalTime toLocalTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private void doAttendanceTimeline(LocalDateTime now) {
        LocalDate yesterday = now.toLocalDate().minusDays(1);

        Crew crew = createCrew();
        MonthlyAttendance monthlyAttendance = attendances.findMonthlyAttendance(crew, now.getMonth());
        AttendanceResult attendanceResult = monthlyAttendance.calculateAttendanceResultUntilDate(yesterday);
        outputView.printAttendanceResult(attendanceResult);
    }

    private Crew createCrew() {
        String nickname = inputView.inputNickname();
        attendances.validateExistNickname(nickname);
        return new Crew(nickname);
    }

    private void doEmergencyCheck(LocalDateTime now) {
        LocalDate yesterday = now.toLocalDate().minusDays(1);
        List<AttendanceResult> attendanceResults = attendances.findAllCrewAttendanceResultUntilDate(yesterday);
        outputView.printEmergencyCrews(attendanceResults);
    }
}
