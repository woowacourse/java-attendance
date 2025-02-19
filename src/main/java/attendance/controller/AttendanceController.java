package attendance.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.view.FileLineReader;
import attendance.view.InputView;

public class AttendanceController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final InputView inputView;

    public AttendanceController(final InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        List<String> firstSkippedLines = readAttendanceFileLinesWithoutFirstLine();
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(firstSkippedLines);
        Map<Crew, Attendances> crewAttendances = createCrewAttendances(crewAttendanceDateTimes);
    }

    private Map<Crew, Attendances> createCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        Map<Crew, Attendances> crewAttendances = new HashMap<>();
        for (Crew crew : crewAttendanceDateTimes.keySet()) {
            Attendances attendances = new Attendances(crewAttendanceDateTimes.get(crew), LocalDateTime.now());
            crewAttendances.put(crew, attendances);
        }
        return crewAttendances;
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

    private List<String> readAttendanceFileLinesWithoutFirstLine() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> lines = fileLineReader.readAllLines("src/main/resources/", "attendances.csv");
        return lines.stream()
                .skip(1L)
                .toList();
    }

}
