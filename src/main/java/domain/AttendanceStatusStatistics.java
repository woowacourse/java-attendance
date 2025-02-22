package domain;

import java.util.Arrays;
import java.util.Map;

public class AttendanceStatusStatistics {
    private final Map<AttendanceStatus, Integer> statusCounter;

    public AttendanceStatusStatistics(Map<AttendanceStatus, Integer> statusCounter) {
        this.statusCounter = statusCounter;
    }

    public int getCountByStatus(AttendanceStatus... statuses) {
        return Arrays.stream(statuses)
                .mapToInt(status -> statusCounter.getOrDefault(status, 0))
                .sum();
    }

    public int calculateTotalAbsentCountForManage() {
        int lateCount = statusCounter.getOrDefault(AttendanceStatus.LATE, 0);
        return statusCounter.getOrDefault(AttendanceStatus.ABSENT_LATE, 0)
                + statusCounter.getOrDefault(AttendanceStatus.ABSENT, 0)
                + lateCount / 3;
    }

    public Map<AttendanceStatus, Integer> getStatusCounter() {
        return statusCounter;
    }
}
