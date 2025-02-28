package attendance.controller;

import attendance.model.AttendanceLog;
import attendance.model.AttendanceLogs;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.AttendancesFile;
import attendance.model.Command;
import attendance.model.EducationSchedule;
import attendance.model.Nickname;
import attendance.model.NicknameRoster;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start(LocalDate baseDate) {
        AttendanceLogs attendanceLogs = loadAttendanceLogs();
        NicknameRoster nicknameRoster = new NicknameRoster(attendanceLogs.getAllNicknames());
        try {
            startAttendanceInteraction(baseDate, attendanceLogs, nicknameRoster);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        } catch (DateTimeParseException e) {
            outputView.printTimeFormatError();
        }
    }

    private void startAttendanceInteraction(LocalDate baseDate,
                                            AttendanceLogs attendanceLogs,
                                            NicknameRoster nicknameRoster) {
        boolean shouldContinue;
        do {
            outputView.printDate(baseDate);
            shouldContinue = processInputCommand(baseDate, attendanceLogs, nicknameRoster);
        } while (shouldContinue);
    }

    private boolean processInputCommand(LocalDate baseDate,
                                        AttendanceLogs attendanceLogs,
                                        NicknameRoster nicknameRoster) {
        Command command = readCommand();
        if (command == Command.QUIT) {
            return false;
        }
        executeCommand(command, baseDate, attendanceLogs, nicknameRoster);
        return true;
    }

    private Command readCommand() {
        String rawCommand = inputView.readCommand(Command.getCommands());
        return Command.from(rawCommand);
    }

    private AttendanceLogs loadAttendanceLogs() {
        return new AttendancesFile().load("src/main/resources/attendances.csv");
    }

    private void executeCommand(Command command,
                                LocalDate baseDate,
                                AttendanceLogs attendanceLogs,
                                NicknameRoster nicknameRoster) {
        if (command == Command.ATTENDANCE) {
            attend(baseDate, attendanceLogs, nicknameRoster);
        }
        if (command == Command.EDIT_ATTENDANCE) {
            editAttendanceLog(baseDate, attendanceLogs, nicknameRoster);
        }
        if (command == Command.ATTENDANCE_LOGS) {
            displayAttendanceLogs(baseDate, attendanceLogs, nicknameRoster);
        }
        if (command == Command.WARNING_LIST) {
            displayWarningList(baseDate, attendanceLogs);
        }
    }

    private void attend(LocalDate baseDate, AttendanceLogs attendanceLogs, NicknameRoster nicknameRoster) {
        Nickname nickname = new Nickname(inputView.readNickname());
        if (nicknameRoster.isMissing(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        LocalTime attendanceTime = parseTime(inputView.readAttendanceTime());
        AttendanceLog attendanceLog = new AttendanceLog(nickname, baseDate, attendanceTime);
        attendanceLogs.add(attendanceLog);
        LocalDateTime attendanceDateTime = attendanceLog.getAttendanceDateTime();
        AttendanceType attendanceType = determineAttendanceType(baseDate, attendanceLog.getAttendanceTime());
        outputView.printAttend(attendanceDateTime, attendanceType);
    }

    private AttendanceType determineAttendanceType(LocalDate baseDate, LocalTime attendanceTime) {
        LocalTime startTimeInBaseDate = EducationSchedule.findStartTimeByDay(baseDate.getDayOfWeek());
        return AttendanceType.determine(startTimeInBaseDate, attendanceTime);
    }

    private LocalTime parseTime(String rawTime) {
        return LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private void editAttendanceLog(LocalDate baseDate, AttendanceLogs attendanceLogs, NicknameRoster nicknameRoster) {
        Nickname nickname = new Nickname(inputView.readNicknameForEditAttendance());
        if (nicknameRoster.isMissing(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
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

    private void displayAttendanceLogs(LocalDate baseDate, AttendanceLogs attendanceLogs,
                                       NicknameRoster nicknameRoster) {
        Nickname nickname = new Nickname(inputView.readNickname());
        if (nicknameRoster.isMissing(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        List<AttendanceLog> logs = attendanceLogs.findAllByNicknameInMonth(nickname, baseDate);
        outputView.printAttendanceLogs(nickname, logs);
        EnumMap<AttendanceType, Integer> counted = attendanceLogs.countAllAttendanceType(nickname, baseDate);
        outputView.printAttendanceTypeCount(counted);
        outputView.printWarningLevel(determineWarningLevel(counted));
    }

    private AttendanceWarningLevel determineWarningLevel(EnumMap<AttendanceType, Integer> counted) {
        int lateCount = counted.get(AttendanceType.LATE);
        int absentCount = counted.get(AttendanceType.ABSENT);
        return AttendanceWarningLevel.determine(lateCount, absentCount);
    }

    // TODO: 분리가 필요합니다. 정렬 요구 사항을 해결하지 못했습니다.
    private void displayWarningList(LocalDate baseDate, AttendanceLogs attendanceLogs) {
        Set<Nickname> nicknames = attendanceLogs.getAllNicknames();
        List<String> warnings = new ArrayList<>();
        for (Nickname nickname : nicknames) {
            EnumMap<AttendanceType, Integer> counted = attendanceLogs.countAllAttendanceType(nickname, baseDate);
            int lateCount = counted.getOrDefault(AttendanceType.LATE, 0);
            int absentCount = counted.getOrDefault(AttendanceType.ABSENT, 0);

            AttendanceWarningLevel warningLevel = AttendanceWarningLevel.determine(lateCount, absentCount);

            if (warningLevel != AttendanceWarningLevel.CLEAN) {
                warnings.add("- %s: 결석 %d회, 지각 %d회 (%s)".formatted(
                        nickname, absentCount, lateCount, warningLevel.getKoreanLabel()
                ));
            }
        }

        if (warnings.isEmpty()) {
            System.out.println("\n제적 위험자가 없습니다.");
            return;
        }
        System.out.println("\n제적 위험자 조회 결과");
        warnings.forEach(System.out::println);
    }
}
