package attendance.controller;

import attendance.model.Attendance;
import attendance.model.AttendanceResult;
import attendance.model.Attendances;
import attendance.model.AttendancesFactory;
import attendance.model.Command;
import attendance.model.Crew;
import attendance.model.MonthlyAttendance;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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

    private boolean start(LocalDateTime startDateTime) {
        Command command = null;
        try {
            command = Command.from(inputView.inputCommand(startDateTime.toLocalDate()));
            startCommand(command, startDateTime);
        } catch (DateTimeException e) {
            outputView.printDateTimeErrorMessage();
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        return command != Command.QUIT;
    }

    private void startCommand(Command command, LocalDateTime startDateTime) {
        if (command == Command.ATTENDANCE) {
            attend(startDateTime);
        }
        if (command == Command.ATTENDANCE_UPDATE) {
            updateAttendance(startDateTime);
        }
        if (command == Command.ATTENDANCE_RESULT) {
            showAttendanceResult(startDateTime);
        }
        if (command == Command.EMERGENCY_SUBJECTS) {
            showEmergencySubjects(startDateTime);
        }
    }

    private void attend(LocalDateTime today) {
        Attendance attendance = createAttendance(today);
        attendances.attend(attendance);
        outputView.printCheckAttendance(attendance);
    }

    private Attendance createAttendance(LocalDateTime today) {
        Crew crew = findCrewByInputNickname();
        LocalDateTime attendanceDateTime = inputAttendanceDateTime(today.toLocalDate());
        return new Attendance(crew, attendanceDateTime);
    }

    private LocalDateTime inputAttendanceDateTime(LocalDate attendanceDate) {
        String rawAttendanceTime = inputView.inputAttendanceTime();
        LocalTime attendanceTime = toLocalTime(rawAttendanceTime);
        return LocalDateTime.of(attendanceDate, attendanceTime);
    }

    private void updateAttendance(LocalDateTime today) {
        Crew crew = inputCrewForUpdate();
        LocalDateTime updateDateTime = createUpdateDateTime(today.getYear(), today.getMonth());
        Attendance beforeAttendance = attendances.findByCrewAndDate(crew, updateDateTime.toLocalDate());
        Attendance modifidedAttendance = attendances.update(today.toLocalDate(), new Attendance(crew, updateDateTime));
        outputView.printModifiedAttendance(beforeAttendance, modifidedAttendance);
    }

    private Crew inputCrewForUpdate() {
        String nickname = inputView.inputNicknameForUpdateAttendance();
        return attendances.findCrewByNickname(nickname);
    }

    private LocalDateTime createUpdateDateTime(int updateYear, Month updateMonth) {
        int targetUpdateDate = inputView.inputDateForUpdateAttendance();
        String rawTimeForUpdate = inputView.inputTimeForUpdateAttendance();

        LocalDate updateDate = LocalDate.of(updateYear, updateMonth, targetUpdateDate);
        return LocalDateTime.of(updateDate, toLocalTime(rawTimeForUpdate));
    }

    private LocalTime toLocalTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private void showAttendanceResult(LocalDateTime today) {
        LocalDate yesterday = today.toLocalDate().minusDays(1);
        Crew crew = findCrewByInputNickname();
        MonthlyAttendance monthlyAttendance = attendances.findMonthlyAttendance(crew, today.getMonth());
        AttendanceResult attendanceResult = monthlyAttendance.calculateAttendanceResultUntilDate(yesterday);
        outputView.printAttendanceResult(attendanceResult);
    }

    private Crew findCrewByInputNickname() {
        String nickname = inputView.inputNickname();
        return attendances.findCrewByNickname(nickname);
    }

    private void showEmergencySubjects(LocalDateTime today) {
        LocalDate yesterday = today.toLocalDate().minusDays(1);
        List<AttendanceResult> attendanceResults = attendances.findAllCrewAttendanceResultUntilDate(yesterday);
        outputView.printEmergencyCrews(attendanceResults);
    }
}
