package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record StatusStatistics(Nickname nickname, Map<AttendanceStatus, Integer> statistics) {
    private static final int WEIGHT_DIVIDER_FOR_LATE = 3;

    public StatusStatistics(Nickname nickname) {
        this(nickname, new HashMap<>());
    }

    public void update(Map<LocalDate, Attendance> attendances) {
        for (LocalDate date : attendances.keySet()) {
            put(attendances.get(date).state());
        }
    }

    public void put(AttendanceStatus state) {
        statistics.put(state, this.get(state) + 1);
    }

    public int get(AttendanceStatus state) {
        return statistics.getOrDefault(state, 0);
    }

    public int getWeight() {
        return statistics.get(AttendanceStatus.LATE) / WEIGHT_DIVIDER_FOR_LATE
            + statistics.get(AttendanceStatus.ABSENCE);
    }
}
