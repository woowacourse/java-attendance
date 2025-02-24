package attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewAttendances;
import attendance.domain.ExpulsionStatus;
import attendance.view.FileLineReader;
import attendance.view.InputView;
import attendance.view.OperationCommand;
import attendance.view.OutputView;

public class AttendanceController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDateTime todayDateTime = LocalDateTime.now();
        CrewAttendances crewAttendances = initializeCrewAttendances(todayDateTime);
        while (true) {
            try {
                outputView.printOperations(todayDateTime.toLocalDate());
                OperationCommand operationCommand = inputView.readOperationCommand();
                if (operationCommand.isQuit()) {
                    break;
                }
                if (operationCommand.isAttendanceConfirmation()) {
                    attendanceConfirmation(crewAttendances, todayDateTime.toLocalDate());
                }
                if (operationCommand.isAttendanceModification()) {
                    modifyAttendance(crewAttendances, todayDateTime.toLocalDate());
                }
                if (operationCommand.isCrewAttendancesCheck()) {
                    inquireCrewAttendances(crewAttendances);
                }
                if (operationCommand.isExpulsionCheck()) {
                    checkExpulsionCrews(crewAttendances);
                }
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private CrewAttendances initializeCrewAttendances(final LocalDateTime todayDateTime) {
        List<String> firstSkippedLines = readAttendanceFileLinesWithoutFirstLine();
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(firstSkippedLines);
        return new CrewAttendances(crewAttendanceDateTimes, todayDateTime);
    }

    private void attendanceConfirmation(final CrewAttendances crewAttendances, final LocalDate today) {
        Crew crew = crewAttendances.findCrewByNickname(inputView.readCrewNickname());
        LocalTime attendanceTime = inputView.readAttendanceTime();
        if (crewAttendances.hasAttendance(crew, today)) {
            outputView.printUsingAttendanceModification();
        }
        saveTodayAttendance(crew, crewAttendances, LocalDateTime.of(today, attendanceTime));
    }

    private void saveTodayAttendance(
            final Crew crew, final CrewAttendances crewAttendances, final LocalDateTime attendanceDateTime
    ) {
        AttendanceDate attendanceDate = new AttendanceDate(attendanceDateTime.toLocalDate());
        AttendanceTime attendanceTime = new AttendanceTime(attendanceDateTime.toLocalTime());
        Attendance attendance = new Attendance(attendanceDate, attendanceTime);
        crewAttendances.addAttendance(crew, attendance);
        String attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDate, attendanceTime).getText();
        outputView.printAttendance(attendance.getAttendanceDateTime(), attendanceStatus);
    }

    private List<String> readAttendanceFileLinesWithoutFirstLine() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> lines = fileLineReader.readAllLines("src/main/resources/", "attendances.csv");
        return lines.stream()
                .skip(1L)
                .toList();
    }

    private void modifyAttendance(final CrewAttendances crewAttendances, final LocalDate today) {
        Crew crew = crewAttendances.findCrewByNickname(inputView.readModificationCrewNickname());
        LocalDate modificationDate = inputView.readModificationDay(today);
        LocalTime modificationTime = inputView.readModificationTime();
        Attendance originAttendance = crewAttendances.findAttendanceByLocalDate(crew, modificationDate);
        Attendance modifiedAttendance = crewAttendances.modifyAttendance(crew, originAttendance, modificationTime);
        printModificationAttendanceResult(originAttendance, modifiedAttendance);
    }

    private void printModificationAttendanceResult(
            final Attendance originAttendance, final Attendance modifiedAttendance
    ) {
        String originAttendanceStatus = AttendanceStatus.findByAttendanceDateTime(
                originAttendance.getAttendanceDate(), originAttendance.getAttendanceTime()).getText();
        String newAttendanceStatus = AttendanceStatus.findByAttendanceDateTime(
                modifiedAttendance.getAttendanceDate(), modifiedAttendance.getAttendanceTime()).getText();
        outputView.printModificationResult(originAttendance.getAttendanceDateTime(), originAttendanceStatus,
                modifiedAttendance.getAttendanceDateTime(), newAttendanceStatus);
    }

    private void inquireCrewAttendances(final CrewAttendances crewAttendances) {
        Crew crew = crewAttendances.findCrewByNickname(inputView.readCrewNickname());
        Attendances attendances = crewAttendances.findAllAttendance(crew);
        List<LocalDateTime> attendanceTimes = attendances.getAttendances().stream()
                .map(Attendance::getAttendanceDateTime)
                .toList();
        List<String> attendanceStatusTexts = attendanceTimes.stream()
                .map(dateTime -> AttendanceStatus.findByAttendanceDateTime(new AttendanceDate(dateTime.toLocalDate()),
                        new AttendanceTime(dateTime.toLocalTime()))
                ).map(AttendanceStatus::getText)
                .toList();
        Map<String, Integer> statusCount = attendances.calculateStatusCount();
        outputView.printAttendances(crew.getNickname(), attendanceTimes, attendanceStatusTexts);
        outputView.printStatusCounts(statusCount);
        ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
        outputView.printExpulsionStatus(expulsionStatus.getText());
    }

    private Map<Crew, List<LocalDateTime>> createAttendanceDateTimes(final List<String> firstSkippedLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = new HashMap<>();
        for (String line : firstSkippedLines) {
            String[] tokens = line.split(",");
            Crew crew = new Crew(tokens[0]);
            LocalDateTime attendanceDateTime = LocalDateTime.parse(tokens[1], DATE_TIME_FORMATTER);
            crewAttendanceDateTimes.computeIfAbsent(crew, value -> new ArrayList<>()).add(attendanceDateTime);
        }
        return crewAttendanceDateTimes;
    }

    private void checkExpulsionCrews(final CrewAttendances crewAttendances) {
        Map<String, AttendanceHistory> attendanceHistories = new HashMap<>();
        for (Map.Entry<Crew, Attendances> entry : crewAttendances.getCrewAttendances().entrySet()) {
            Attendances attendances = entry.getValue();
            ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
            AttendanceHistory attendanceHistory = new AttendanceHistory(attendances.calculateTotalAbsentCount(),
                    attendances.calculateTotalLateCount(), expulsionStatus.getText());
            attendanceHistories.put(entry.getKey().getNickname(), attendanceHistory);
        }
        outputView.printExpulsionCrews(attendanceHistories);
    }

}
