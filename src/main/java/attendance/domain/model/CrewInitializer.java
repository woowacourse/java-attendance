package attendance.domain.model;

import static attendance.domain.model.AttendanceType.DEFAULT_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CrewInitializer {

    private final Campus campus;
    private final TodayClock todayClock;
    private final Map<LocalDate, LocalDateTime> attendance;

    public CrewInitializer(final Campus campus, final TodayClock todayClock) {
        this.campus = campus;
        this.todayClock = todayClock;
        this.attendance = createInitialAttendance();
    }

    public CrewHistories initialize(final Map<String, List<LocalDateTime>> histories) {
        Map<String, CrewHistory> crewsMap = new HashMap<>();
        for (Entry<String, List<LocalDateTime>> entry : histories.entrySet()) {
            addCrewHistory(entry, crewsMap);
        }
        return new CrewHistories(crewsMap);
    }

    private void addCrewHistory(final Entry<String, List<LocalDateTime>> entry,
                                final Map<String, CrewHistory> crewsMap) {
        CrewHistory crewHistory = getCrew(entry.getKey(), crewsMap);
        for (LocalDateTime time : entry.getValue()) {
            crewHistory.loadHistory(time);
        }
    }

    private Map<LocalDate, LocalDateTime> createInitialAttendance() {
        Map<LocalDate, LocalDateTime> initialAttendance = new HashMap<>();
        LocalDate now = todayClock.getTodayDate();
        for (int day = 1; day < now.getDayOfMonth(); day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            putOperationDate(date, initialAttendance);
        }
        return initialAttendance;
    }

    private void putOperationDate(final LocalDate date, final Map<LocalDate, LocalDateTime> initialAttendance) {
        if (campus.isNotOperationDate(date)) {
            return;
        }
        LocalDateTime dateTime = LocalDateTime.of(date, DEFAULT_TIME);
        initialAttendance.put(date, dateTime);
    }

    private CrewHistory getCrew(final String nickname, Map<String, CrewHistory> inputs) {
        if (inputs.containsKey(nickname)) {
            return inputs.get(nickname);
        }
        CrewHistory crewHistory = new CrewHistory(new HashMap<>(attendance));
        inputs.put(nickname, crewHistory);
        return crewHistory;
    }
}
