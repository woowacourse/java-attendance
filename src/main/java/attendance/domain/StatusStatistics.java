package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public record StatusStatistics(Map<AttendanceStatus, Integer> statistics) {
    public StatusStatistics() {
        this(new HashMap<>());
    }

    public void put(AttendanceStatus state) {
        statistics.put(state, this.get(state) + 1);
    }

    public int get(AttendanceStatus state) {
        return statistics.getOrDefault(state, 0);
    }

    public int getWeight() {
        return statistics.get(AttendanceStatus.LATE) / 3 + statistics.get(AttendanceStatus.ABSENCE);
    }
}
