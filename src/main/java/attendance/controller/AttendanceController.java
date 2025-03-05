package attendance.controller;

import attendance.dto.AttendanceLogDto;
import attendance.dto.AttendanceWarningDto;
import attendance.model.AttendanceBook;
import attendance.model.AttendanceLog;
import attendance.model.AttendanceLogs;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.AttendancesFile;
import attendance.model.Command;
import attendance.model.Nickname;
import attendance.model.NicknameRegistry;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
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
        attendanceBook.attend(nickname, attendanceDateTime);
        displayAttendanceLog(baseDate, attendanceBook, nickname);
    }

    private Nickname readNickname(AttendanceBook attendanceBook) {
        final Nickname nickname = new Nickname(inputView.readNickname());
        attendanceBook.validateNicknameExists(nickname);
        return nickname;
    }

    private LocalDateTime readAttendanceDateTime(LocalDate baseDate) {
        final LocalTime attendanceTime = inputView.readAttendanceTime();
        return LocalDateTime.of(baseDate, attendanceTime);
    }

    private void displayAttendanceLog(LocalDate baseDate, AttendanceBook attendanceBook, Nickname nickname) {
        final LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(nickname, baseDate);
        final AttendanceType attendanceType = attendanceBook.determineAttendanceType(baseDate, attendanceTime);
        outputView.printAttendanceLog(LocalDateTime.of(baseDate, attendanceTime), attendanceType);
    }

    private void editAttendanceLog(LocalDate baseDate, AttendanceBook attendanceBook) {
        final Nickname nickname = readNicknameForEditAttendance(attendanceBook);
        final LocalDateTime updateDateTime = readUpdateDateTime(baseDate);
        displayBeforeAttendanceLog(updateDateTime.toLocalDate(), attendanceBook, nickname);
        attendanceBook.edit(nickname, updateDateTime);
        displayAfterAttendanceLog(updateDateTime.toLocalDate(), attendanceBook, nickname);
    }

    private void displayBeforeAttendanceLog(LocalDate baseDate, AttendanceBook attendanceBook, Nickname nickname) {
        final LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(nickname, baseDate);
        final AttendanceType attendanceType = attendanceBook.determineAttendanceType(baseDate, attendanceTime);
        outputView.printAttendanceLog(baseDate, attendanceTime, attendanceType);
    }

    private void displayAfterAttendanceLog(LocalDate baseDate, AttendanceBook attendanceBook, Nickname nickname) {
        final LocalTime attendanceTime = attendanceBook.findAttendanceTimeByNicknameAndDate(nickname, baseDate);
        final AttendanceType attendanceType = attendanceBook.determineAttendanceType(baseDate, attendanceTime);
        outputView.printEditAttendanceLog(attendanceTime, attendanceType);
    }

    private Nickname readNicknameForEditAttendance(AttendanceBook attendanceBook) {
        final Nickname nickname = new Nickname(inputView.readNicknameForEditAttendance());
        attendanceBook.validateNicknameExists(nickname);
        return nickname;
    }

    private LocalDateTime readUpdateDateTime(LocalDate baseDate) {
        final int updateDay = inputView.readDateForEditAttendance();
        final LocalDate updateDate = LocalDate.of(baseDate.getYear(), baseDate.getMonth(), updateDay);
        final LocalTime updateTime = inputView.readAttendanceTimeForEditAttendance();
        return LocalDateTime.of(updateDate, updateTime);
    }

    private void displayAttendanceLogs(LocalDate baseDate, AttendanceBook attendanceBook) {
        final Nickname nickname = readNickname(attendanceBook);
        final List<AttendanceLog> logs = attendanceBook.findAttendanceLogsByNicknameAndInMonth(nickname, baseDate);
        final EnumMap<AttendanceType, Integer> typeCounts = attendanceBook.countAttendanceTypes(nickname, baseDate);
        displayAttendanceLogs(attendanceBook, nickname, logs);
        displayAttendanceTypeCountsAndWarningLevel(attendanceBook, typeCounts);
    }

    private void displayAttendanceTypeCountsAndWarningLevel(AttendanceBook attendanceBook,
                                                            EnumMap<AttendanceType, Integer> typeCounts) {
        outputView.printAttendanceTypeCounts(typeCounts);
        outputView.printWarningLevel(attendanceBook.determineWarningLevel(typeCounts));
    }

    private void displayAttendanceLogs(AttendanceBook attendanceBook, Nickname nickname, List<AttendanceLog> logs) {
        List<AttendanceLogDto> attendanceLogDtos = AttendanceLogDto.mapToDtos(attendanceBook, logs);
        outputView.printAttendanceLogs(nickname, attendanceLogDtos);
    }

    private void displayWarningList(LocalDate baseDate, AttendanceBook attendanceBook) {
        List<AttendanceWarningDto> attendanceWarningDtos = createAttendanceWarningDtos(baseDate, attendanceBook);
        outputView.printWarningList(attendanceWarningDtos);
    }

    private List<AttendanceWarningDto> createAttendanceWarningDtos(LocalDate baseDate, AttendanceBook attendanceBook) {
        return attendanceBook.getNicknames()
                .stream()
                .map(nickname -> AttendanceWarningDto.from(baseDate, attendanceBook, nickname))
                .filter(this::isNotCleanLevel)
                .sorted(getAttendanceWarningComparator())
                .toList();
    }

    private boolean isNotCleanLevel(AttendanceWarningDto attendanceWarningDto) {
        return attendanceWarningDto.attendanceLevel() != AttendanceWarningLevel.CLEAN;
    }

    private Comparator<AttendanceWarningDto> getAttendanceWarningComparator() {
        return this::compareAttendanceWarningLevelDesc;
    }

    private int compareAttendanceWarningLevelDesc(AttendanceWarningDto first, AttendanceWarningDto second) {
        if (first.attendanceLevel() == second.attendanceLevel()) {
            return compareAbsentCountTotalDesc(first, second);
        }
        return second.attendanceLevel().compareTo(first.attendanceLevel());
    }

    private int compareAbsentCountTotalDesc(AttendanceWarningDto first, AttendanceWarningDto second) {
        int firstAbsentCountTotal = first.absentCount() + (first.lateCount() / 3);
        int secondAbsentCountTotal = second.absentCount() + (second.lateCount() / 3);
        if (firstAbsentCountTotal == secondAbsentCountTotal) {
            return compareNicknameAsc(first, second);
        }
        return firstAbsentCountTotal - second.absentCount() + (second.lateCount() / 3);
    }

    private int compareNicknameAsc(AttendanceWarningDto first, AttendanceWarningDto second) {
        return first.nickname().compareTo(second.nickname());
    }
}
