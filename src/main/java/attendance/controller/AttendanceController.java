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
        List<String> firstSkippedLines = readAttendanceFileLinesWithoutFirstLine();
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(firstSkippedLines);
        Map<Crew, Attendances> crewAttendances = createCrewAttendances(crewAttendanceDateTimes);

        LocalDate today = LocalDate.now();
        try {
            while (true) {
                outputView.printOperations(today);
                OperationCommand operationCommand = inputView.readOperationCommand();
                if (operationCommand.isQuit()) {
                    break;
                }
                if (operationCommand.isAttendanceConfirmation()) {
                    attendanceConfirmation(crewAttendances, today);
                }
                if (operationCommand.isAttendanceModification()) {
                    modifyAttendance(crewAttendances, today);
                }
                if (operationCommand.isCrewAttendancesCheck()) {
                    checkCrewAttendances(crewAttendances);
                }
                if (operationCommand.isExpulsionCheck()) {
                    checkExpulsionCrews(crewAttendances);
                }
            }
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void modifyAttendance(Map<Crew, Attendances> crewAttendances, LocalDate today) {
        String nickname = inputView.readModificationCrewNickname();
        Crew crew = new Crew(nickname);
        validateCrewExistence(crewAttendances, crew);

        LocalDate modificationDate = inputView.readModificationDay(today);
        LocalTime modificationTime = inputView.readModificationTime();
        Attendances attendances = crewAttendances.get(crew);
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

    private void checkCrewAttendances(final Map<Crew, Attendances> crewAttendances) {
        Crew crew = new Crew(inputView.readCrewNickname());
        validateCrewExistence(crewAttendances, crew);
        Attendances attendances = crewAttendances.get(crew);
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
        Map<String, Integer> statusCount = attendances.calculateStatusCount();
        outputView.printAttendances(crew.getNickname(), attendanceTimes, attendanceStatusTexts);
        outputView.printStatusCounts(statusCount);
        ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
        outputView.printExpulsionStatus(expulsionStatus.getText());
    }


    private List<String> readAttendanceFileLinesWithoutFirstLine() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> lines = fileLineReader.readAllLines("src/main/resources/", "attendances.csv");
        return lines.stream()
                .skip(1L)
                .toList();
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

    private Map<Crew, Attendances> createCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        Map<Crew, Attendances> crewAttendances = new HashMap<>();
        for (Crew crew : crewAttendanceDateTimes.keySet()) {
            Attendances attendances = new Attendances(crewAttendanceDateTimes.get(crew), LocalDateTime.now());
            crewAttendances.put(crew, attendances);
        }
        return crewAttendances;
    }

    private void attendanceConfirmation(final Map<Crew, Attendances> crewAttendances, final LocalDate today) {
        String nickName = inputView.readCrewNickname();
        Crew crew = new Crew(nickName);
        LocalTime attendanceTime = inputView.readAttendanceTime();
        validateCrewExistence(crewAttendances, crew);
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

    private void validateCrewExistence(final Map<Crew, Attendances> crewAttendances, final Crew crew) {
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    private void checkExpulsionCrews(final Map<Crew, Attendances> crewAttendances) {
        Map<String, AttendanceHistory> attendanceHistories = new HashMap<>();
        for (Map.Entry<Crew, Attendances> entry : crewAttendances.entrySet()) {
            Attendances attendances = entry.getValue();
            ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
            AttendanceHistory attendanceHistory = new AttendanceHistory(attendances.calculateTotalAbsentCount(),
                    attendances.calculateTotalLateCount(), expulsionStatus.getText());
            attendanceHistories.put(entry.getKey().getNickname(), attendanceHistory);
        }
        outputView.printExpulsionCrews(attendanceHistories);
    }

}
