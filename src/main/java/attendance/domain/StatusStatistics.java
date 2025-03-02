package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public record StatusStatistics(Map<AttendanceStatus, Integer> history) {
    public StatusStatistics() {
        this(new HashMap<>());
    }

    public void put(AttendanceStatus state) {
        history.put(state, this.get(state) + 1);
    }

    public int get(AttendanceStatus state) {
        return history.getOrDefault(state, 0);
    }
}
