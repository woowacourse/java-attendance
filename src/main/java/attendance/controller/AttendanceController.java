package attendance.controller;

import attendance.dto.AttendResult;
import attendance.dto.AttendanceLogDto;
import attendance.dto.EditResult;
import attendance.dto.AttendanceWarning;
import attendance.model.AttendanceBook;
import attendance.model.AttendanceLogs;
import attendance.model.AttendanceType;
import attendance.model.AttendancesFile;
import attendance.model.Command;
import attendance.model.Nickname;
import attendance.model.NicknameRegistry;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EnumMap;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start(LocalDate baseDate) {
        final AttendanceBook attendanceBook = createAttendanceBook();
        try {
            startAttendanceInteraction(baseDate, attendanceBook);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        } catch (DateTimeParseException e) {
            outputView.printTimeFormatError();
        }
    }

    private AttendanceBook createAttendanceBook() {
        final AttendanceLogs attendanceLogs = new AttendancesFile().load("src/main/resources/attendances.csv");
        final NicknameRegistry nicknameRegistry = new NicknameRegistry(attendanceLogs.getAllNicknames());
        return new AttendanceBook(attendanceLogs, nicknameRegistry);
    }

    private void startAttendanceInteraction(LocalDate baseDate, AttendanceBook attendanceBook) {
        boolean shouldContinue;
        do {
            outputView.printDate(baseDate);
            shouldContinue = processInputCommand(baseDate, attendanceBook);
        } while (shouldContinue);
    }

    private boolean processInputCommand(LocalDate baseDate, AttendanceBook attendanceBook) {
        final Command command = Command.from(inputView.readCommand(Command.getCommands()));
        if (command == Command.QUIT) {
            return false;
        }
        executeCommand(command, baseDate, attendanceBook);
        return true;
    }

    private void executeCommand(Command command, LocalDate baseDate, AttendanceBook attendanceBook) {
        if (command == Command.ATTENDANCE) {
            attend(baseDate, attendanceBook);
        }
        if (command == Command.EDIT_ATTENDANCE) {
            editAttendanceLog(baseDate, attendanceBook);
        }
        if (command == Command.ATTENDANCE_LOGS) {
            displayAttendanceLogs(baseDate, attendanceBook);
        }
        if (command == Command.WARNING_LIST) {
            displayWarningList(baseDate, attendanceBook);
        }
    }

    private void attend(LocalDate baseDate, AttendanceBook attendanceBook) {
        final Nickname nickname = readNickname(attendanceBook);
        final LocalDateTime attendanceDateTime = readAttendanceDateTime(baseDate);
        final AttendResult attendResult = attendanceBook.attend(nickname, attendanceDateTime);
        outputView.printAttendance(attendResult);
    }

    private Nickname readNickname(AttendanceBook attendanceBook) {
        final Nickname nickname = new Nickname(inputView.readNickname());
        attendanceBook.validateNicknameExists(nickname);
        return nickname;
    }

    private LocalDateTime readAttendanceDateTime(LocalDate baseDate) {
        final LocalTime attendanceTime = parseTime(inputView.readAttendanceTime());
        return LocalDateTime.of(baseDate, attendanceTime);
    }

    private LocalTime parseTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private void editAttendanceLog(LocalDate baseDate, AttendanceBook attendanceBook) {
        final Nickname nickname = readNicknameForEditAttendance(attendanceBook);
        final LocalDateTime updateDateTime = readUpdateDateTime(baseDate);
        final EditResult editResult = attendanceBook.edit(nickname, updateDateTime);
        outputView.printEditAttendanceLog(editResult);
    }

    private Nickname readNicknameForEditAttendance(AttendanceBook attendanceBook) {
        final Nickname nickname = new Nickname(inputView.readNicknameForEditAttendance());
        attendanceBook.validateNicknameExists(nickname);
        return nickname;
    }

    private LocalDateTime readUpdateDateTime(LocalDate baseDate) {
        final int updateDay = inputView.readDateForEditAttendance();
        final LocalDate updateDate = LocalDate.of(baseDate.getYear(), baseDate.getMonth(), updateDay);
        final LocalTime updateTime = parseTime(inputView.readAttendanceTimeForEditAttendance());
        return LocalDateTime.of(updateDate, updateTime);
    }

    private void displayAttendanceLogs(LocalDate baseDate, AttendanceBook attendanceBook) {
        final Nickname nickname = readNickname(attendanceBook);
        final List<AttendanceLogDto> logs = attendanceBook.findAttendanceLogsByNicknameAndInMonth(nickname, baseDate);
        outputView.printAttendanceLogs(nickname, logs);
        final EnumMap<AttendanceType, Integer> attendanceTypeCounts = attendanceBook.countAllAttendanceType(nickname, baseDate);
        outputView.printAttendanceTypeCount(attendanceTypeCounts);
        outputView.printWarningLevel(attendanceBook.determineWarningLevel(attendanceTypeCounts));
    }

    private void displayWarningList(LocalDate baseDate, AttendanceBook attendanceBook) {
        final List<AttendanceWarning> attendanceWarnings = attendanceBook.getAttendanceWarnings(baseDate);
        outputView.printWarningList(attendanceWarnings);
    }
}
