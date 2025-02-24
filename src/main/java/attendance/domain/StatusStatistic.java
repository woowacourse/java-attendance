package attendance.domain;

import java.util.Map;

public record StatusStatistic(Map<AttendanceStatus, Integer> statistic) {
    private static final String STATE_TOTAL = "\n%s: %d회";

    public String getReport() {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (AttendanceStatus attendanceStatus : statistic.keySet()) {
            String status = attendanceStatus.getStatus();
            int count = statistic.get(attendanceStatus);
            var formatted = String.format(STATE_TOTAL, status, count);
            stringBuilder.append(formatted);
        }

        return stringBuilder.toString();
    }
}
