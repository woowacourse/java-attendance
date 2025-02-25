package domain.model;

import static domain.model.AttendanceType.DEFAULT_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.StringParser;

public class CrewInitializer {

    private static final String SPLITTER = ",";

    private final Campus campus;
    private final TodayClock todayClock;
    private final Map<Integer, LocalDateTime> attendance;

    public CrewInitializer(final Campus campus, final TodayClock todayClock) {
        this.campus = campus;
        this.todayClock = todayClock;
        this.attendance = createInitialAttendance();
    }

    public CrewHistories initialize(final List<String> inputs) {
        Map<String, CrewHistory> crewsMap = new HashMap<>();
        for (String line : inputs) {
            loadHistory(line, crewsMap);
        }
        return new CrewHistories(crewsMap);
    }

    private Map<Integer, LocalDateTime> createInitialAttendance() {
        Map<Integer, LocalDateTime> initialAttendance = new HashMap<>();
        LocalDate now = todayClock.getTodayDate();
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

    private void loadHistory(String input, Map<String, CrewHistory> crewsMap) {
        String[] tokens = input.split(SPLITTER);
        String nickname = tokens[0];
        LocalDateTime attendanceDateTime = StringParser.parseLocalDateTime(tokens[1]);
        CrewHistory crewHistory = getCrew(crewsMap, nickname);
        crewHistory.loadHistory(attendanceDateTime);
    }

    private CrewHistory getCrew(final Map<String, CrewHistory> crewsMap, final String nickname) {
        if (crewsMap.containsKey(nickname)) {
            return crewsMap.get(nickname);
        }
        CrewHistory crewHistory = new CrewHistory(new HashMap<>(attendance));
        crewsMap.put(nickname, crewHistory);
        return crewHistory;
    }
}
