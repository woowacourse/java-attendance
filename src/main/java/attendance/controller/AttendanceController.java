package attendance.controller;

import attendance.model.Attendance;
import attendance.model.AttendanceBook;
import attendance.model.AttendanceBookFactory;
import attendance.model.AttendanceResult;
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
    private final AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        attendanceBook = new AttendanceBookFactory().initialize();
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
        attendanceBook.attend(attendance);
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
        Attendance beforeAttendance = attendanceBook.findByCrewAndDate(crew, updateDateTime.toLocalDate());
        Attendance modifidedAttendance = attendanceBook.update(today.toLocalDate(),
                new Attendance(crew, updateDateTime));
        outputView.printModifiedAttendance(beforeAttendance, modifidedAttendance);
    }

    private Crew inputCrewForUpdate() {
        String nickname = inputView.inputNicknameForUpdateAttendance();
        return attendanceBook.findCrewByNickname(nickname);
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
        MonthlyAttendance monthlyAttendance = attendanceBook.findMonthlyAttendance(crew, today.getMonth());
        AttendanceResult attendanceResult = monthlyAttendance.calculateAttendanceResultUntilDate(yesterday);
        outputView.printAttendanceResult(attendanceResult);
    }

    private Crew findCrewByInputNickname() {
        String nickname = inputView.inputNickname();
        return attendanceBook.findCrewByNickname(nickname);
    }

    private void showEmergencySubjects(LocalDateTime today) {
        LocalDate yesterday = today.toLocalDate().minusDays(1);
        List<AttendanceResult> attendanceResults = attendanceBook.createAttendanceResultOfAllCrewUntilDate(yesterday);
        outputView.printEmergencyCrews(attendanceResults);
    }
}
