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
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.Crew;
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
        outputView.printOperations(today);
        try {
            OperationCommand operationCommand = inputView.readOperationCommand();
            if (operationCommand.isAttendanceConfirmation()) {
                attendanceConfirmation(crewAttendances, today);
            }
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
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
        Attendance attendance = new Attendance(attendanceDateTime);
        attendances.addAttendance(attendance);
        String attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime)
                .getText();
        outputView.printAttendance(attendance.getAttendanceDateTime(), attendanceStatus);
    }

    private void validateCrewExistence(final Map<Crew, Attendances> crewAttendances, final Crew crew) {
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

}
