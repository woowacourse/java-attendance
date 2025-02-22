package model;

import static model.AttendanceType.DEFAULT_TIME;

import controller.AttendanceController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.FileReader;
import util.StringParser;

public class Initializer {

    private static final String SPLITTER = ",";

    private final Campus campus;
    private final Map<Integer, LocalDateTime> initialMap;

    public Initializer(final Campus campus) {
        this.campus = campus;
        this.initialMap = createInitialAttendance();
    }

    public Crews initialize() {
        Map<String, Crew> crewsMap = new HashMap<>();
        List<String> lines = FileReader.readFile();
        for (String line : lines) {
            loadAttendanceHistory(line, crewsMap);
        }
        return new Crews(crewsMap);
    }

    private void loadAttendanceHistory(String line, Map<String, Crew> crewsMap) {
        String[] tokens = line.split(SPLITTER);
        String nickname = tokens[0];
        LocalDateTime attendanceDateTime = StringParser.parseLocalDateTime(tokens[1]);
        Crew crew = getCrew(crewsMap, nickname);
        crew.loadAttendanceHistory(attendanceDateTime);
    }

    private Crew getCrew(final Map<String, Crew> crewsMap, final String nickname) {
        if (crewsMap.containsKey(nickname)) {
            return crewsMap.get(nickname);
        }
        Crew crew = new Crew(nickname, new HashMap<>(initialMap));
        crewsMap.put(nickname, crew);
        return crew;
    }

    private Map<Integer, LocalDateTime> createInitialAttendance() {
        Map<Integer, LocalDateTime> initialAttendance = new HashMap<>();
        LocalDate now = AttendanceController.getTodayDate();
        for (int day = 1; day < now.getDayOfMonth(); day++) {
            putOperationDate(day, initialAttendance);
        }
        return initialAttendance;
    }

    private void putOperationDate(final int day, final Map<Integer, LocalDateTime> initialAttendance) {
        LocalDate date = LocalDate.of(2024, 12, day);
        if (campus.isNotOperationDate(date)) {
            return;
        }
        LocalDateTime dateTime = LocalDateTime.of(date, DEFAULT_TIME);
        initialAttendance.put(day, dateTime);
    }
}
