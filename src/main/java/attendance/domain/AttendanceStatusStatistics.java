package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public record AttendanceStatusStatistics(Map<AttendanceStatus, Integer> history) {
    public AttendanceStatusStatistics() {
        this(new HashMap<>());
    }

    public void put(AttendanceStatus state) {
        history.put(state, this.get(state) + 1);
    }

    public int get(AttendanceStatus state) {
        return history.getOrDefault(state, 0);
    }
}
