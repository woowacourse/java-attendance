package attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDate;
import attendance.dto.AttendanceHistoryDto;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.ExpulsionStatus;
import attendance.view.FileLineReader;
import attendance.view.InputView;
import attendance.domain.OperationCommand;
import attendance.view.OutputView;

public class AttendanceController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String attendanceFilePath = "src/main/resources/";
    public static final String attendanceFileName = "attendances.csv";
    public static final String attendanceFileDelimiter = ",";
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Crew, Attendances> crewAttendances;
    private final Map<OperationCommand, Runnable> operationMapper = Map.of(
            OperationCommand.ATTENDANCE_CONFIRMATION, this::confirmAttendance
            , OperationCommand.ATTENDANCE_MODIFICATION, this::modifyAttendance
            , OperationCommand.CREW_ATTENDANCES_CHECK, this::checkCrewAttendances
            , OperationCommand.EXPULSION_CHECK, this::checkExpulsionCrews
            , OperationCommand.QUIT, () -> System.exit(0));

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendances = new HashMap<>();
    }

    public void run() {
        initializeCrewAttendances();
        while(true) {
            branchByOperationCommand();
        }
    }

    private void initializeCrewAttendances() {
        List<String> firstSkippedLines = readAttendanceFileLinesWithoutFirstLine();
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(firstSkippedLines);
        createCrewAttendances(crewAttendanceDateTimes);
    }

    private List<String> readAttendanceFileLinesWithoutFirstLine() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> lines = fileLineReader.readAllLines(attendanceFilePath, attendanceFileName);
        return lines.stream()
                .skip(1L)
                .toList();
    }

    private Map<Crew, List<LocalDateTime>> createAttendanceDateTimes(final List<String> firstSkippedLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = new HashMap<>();
        for (String line : firstSkippedLines) {
            String[] tokens = line.split(attendanceFileDelimiter);
            Crew crew = new Crew(tokens[0]);
            LocalDateTime attendanceDateTime = LocalDateTime.parse(tokens[1], DATE_TIME_FORMATTER);
            crewAttendanceDateTimes.computeIfAbsent(crew, value -> new ArrayList<>()).add(attendanceDateTime);
        }
        return crewAttendanceDateTimes;
    }

    private void createCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        for (Crew crew : crewAttendanceDateTimes.keySet()) {
            Attendances attendances = new Attendances(crewAttendanceDateTimes.get(crew), LocalDateTime.now());
            crewAttendances.put(crew, attendances);
        }
    }

    private void branchByOperationCommand() {
        try {
            OperationCommand operationCommand = requestOperationCommand();
            operationMapper.get(operationCommand).run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private OperationCommand requestOperationCommand() {
        outputView.printOperations();
        OperationCommand operationCommand = inputView.readOperationCommand();
        return operationCommand;
    }

    private void confirmAttendance() {
        LocalDate today = LocalDate.now();
        Crew crew = createCrewByNickname(inputView.readCrewNickname());
        LocalTime attendanceTime = inputView.readAttendanceTime();
        Attendances attendances = crewAttendances.get(crew);
        if (attendances.existsByLocalDate(today)) {
            outputView.printUsingAttendanceModification();
        }
        saveTodayAttendance(attendances, LocalDateTime.of(today, attendanceTime));
    }

    private void saveTodayAttendance(final Attendances attendances, final LocalDateTime attendanceDateTime) {
        AttendanceDate attendanceDate = new AttendanceDate(attendanceDateTime.toLocalDate());
        AttendanceTime attendanceTime = new AttendanceTime(attendanceDateTime.toLocalTime());
        Attendance attendance = new Attendance(attendanceDate, attendanceTime);
        attendances.addAttendance(attendance);
        String attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDate, attendanceTime)
                .getText();
        outputView.printAttendance(attendance.getAttendanceDateTime(), attendanceStatus);
    }

    private void modifyAttendance() {
        Crew crew = createCrewByNickname(inputView.readCrewNickname());
        LocalDate modificationDate = inputView.readModificationDay(LocalDate.now());
        LocalTime modificationTime = inputView.readModificationTime();
        Attendances attendances = crewAttendances.get(crew);
        exchangeOldAttendanceToNew(attendances, modificationDate, modificationTime);
    }

    private void exchangeOldAttendanceToNew(Attendances attendances, LocalDate modificationDate, LocalTime modificationTime) {
        Attendance originAttendance = attendances.findAttendanceByLocalDate(modificationDate);
        Attendance newAttendance = originAttendance.changeAttendanceTime(modificationTime);
        attendances.remove(originAttendance);
        attendances.addAttendance(newAttendance);
        String originAttendanceStatus = AttendanceStatus.findByAttendanceDateTime(
                originAttendance.getAttendanceDate(), originAttendance.getAttendanceTime()).getText();
        String newAttendanceStatus = AttendanceStatus.findByAttendanceDateTime(new AttendanceDate(modificationDate),
                new AttendanceTime(modificationTime)).getText();
        outputView.printModificationResult(originAttendance.getAttendanceDateTime(), originAttendanceStatus,
                newAttendance.getAttendanceDateTime(), newAttendanceStatus);
    }

    private void checkCrewAttendances() {
        Crew crew = createCrewByNickname(inputView.readCrewNickname());
        Attendances attendances = crewAttendances.get(crew);
        checkAttendanceRecords(attendances, crew);
        checkAttendanceStatus(attendances);
        checkExpulsionStatus(attendances);
    }

    private void checkAttendanceRecords(Attendances attendances, Crew crew) {
        List<LocalDateTime> attendanceTimes = attendances.getAttendances().stream()
                .map(Attendance::getAttendanceDateTime)
                .toList();
        List<AttendanceStatus> attendanceStatus = attendanceTimes.stream()
                .map(dateTime -> AttendanceStatus.findByAttendanceDateTime(new AttendanceDate(dateTime.toLocalDate()),
                        new AttendanceTime(dateTime.toLocalTime())))
                .toList();
        List<String> attendanceStatusTexts = attendanceStatus.stream()
                .map(AttendanceStatus::getText)
                .toList();
        outputView.printAttendances(crew.getNickname(), attendanceTimes, attendanceStatusTexts);
    }

    private void checkAttendanceStatus(Attendances attendances) {
        Map<String, Integer> statusCount = attendances.calculateStatusCount();
        outputView.printStatusCounts(statusCount);
    }

    private void checkExpulsionStatus(Attendances attendances) {
        ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
        outputView.printExpulsionStatus(expulsionStatus.getText());
    }

    private void checkExpulsionCrews() {
        Map<String, AttendanceHistoryDto> attendanceHistories = new HashMap<>();
        for (Map.Entry<Crew, Attendances> entry : crewAttendances.entrySet()) {
            Attendances attendances = entry.getValue();
            Map<String, Integer> attendanceStatusCounts = attendances.calculateStatusCount();
            ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
            AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(attendanceStatusCounts.get(AttendanceStatus.ABSENT.getText()),
                    attendanceStatusCounts.get(AttendanceStatus.LATE.getText()), expulsionStatus.getText());
            attendanceHistories.put(entry.getKey().getNickname(), attendanceHistoryDto);
        }
        outputView.printExpulsionCrews(attendanceHistories);
    }

    private Crew createCrewByNickname(String nickname) {
        Crew crew = new Crew(nickname);
        validateCrewExistence(crew);
        return crew;
    }


    private void validateCrewExistence(final Crew crew) {
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

}
