package attendance.controller;

import attendance.model.AttendanceLog;
import attendance.model.AttendanceLogs;
import attendance.model.AttendanceType;
import attendance.model.AttendancesFile;
import attendance.model.Command;
import attendance.model.EducationSchedule;
import attendance.model.Nickname;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start(LocalDate baseDate) {
        AttendanceLogs attendanceLogs = new AttendancesFile().load("src/main/resources/attendances.csv");
        outputView.printDate(baseDate);
        String rawCommand = inputView.readCommand(Command.getCommands());
        Command command = Command.from(rawCommand);
        executeCommand(command, baseDate, attendanceLogs);
    }

    private void executeCommand(Command command, LocalDate baseDate, AttendanceLogs attendanceLogs) {
        if (command == Command.ATTENDANCE) {
            attend(baseDate, attendanceLogs);
        }
        if (command == Command.EDIT_ATTENDANCE) {
            editAttendanceLog(baseDate, attendanceLogs);
        }
    }

    private void attend(LocalDate baseDate, AttendanceLogs attendanceLogs) {
        AttendanceLog attendanceLog = createAttendanceLog(baseDate);
        attendanceLogs.add(attendanceLog);
        LocalDateTime attendanceDateTime = attendanceLog.getAttendanceDateTime();
        AttendanceType attendanceType = determineAttendanceType(baseDate, attendanceLog.getAttendanceTime());
        outputView.printAttend(attendanceDateTime, attendanceType);
    }

    private AttendanceLog createAttendanceLog(LocalDate baseDate) {
        Nickname nickname = new Nickname(inputView.readNickname());
        LocalTime attendanceTime = parseTime(inputView.readAttendanceTime());
        return new AttendanceLog(nickname, baseDate, attendanceTime);
    }

    private AttendanceType determineAttendanceType(LocalDate baseDate, LocalTime attendanceTime) {
        LocalTime startTimeInBaseDate = EducationSchedule.findStartTimeByDay(baseDate.getDayOfWeek());
        return AttendanceType.determine(startTimeInBaseDate, attendanceTime);
    }

    private LocalTime parseTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private void editAttendanceLog(LocalDate baseDate, AttendanceLogs attendanceLogs) {
        Nickname nickname = new Nickname(inputView.readNicknameForEditAttendance());
        LocalDate targetDate = createTargetDate(baseDate);
        LocalTime updateTime = parseTime(inputView.readAttendanceTimeForEditAttendance());
        AttendanceLog beforeAttendanceLog = attendanceLogs.findByNicknameAndAttendanceDate(nickname, targetDate);
        AttendanceLog afterAttendanceLog = attendanceLogs.edit(nickname, LocalDateTime.of(targetDate, updateTime));
        outputView.printEditAttendanceLog(
                beforeAttendanceLog,
                determineAttendanceType(targetDate, getTimeInAttendanceLog(beforeAttendanceLog)),
                afterAttendanceLog,
                determineAttendanceType(targetDate, getTimeInAttendanceLog(afterAttendanceLog)));
    }

    private LocalTime getTimeInAttendanceLog(AttendanceLog attendanceLog) {
        if (attendanceLog.isNotRecorded()) {
            return null;
        }
        return attendanceLog.getAttendanceTime();
    }

    private LocalDate createTargetDate(LocalDate baseDate) {
        int targetDate = inputView.readDateForEditAttendance();
        return LocalDate.of(baseDate.getYear(), baseDate.getMonth(), targetDate);
    }
}
