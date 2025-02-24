package attendance.domain;

import java.util.Map;

public record StatusStatistic(Map<AttendanceStatus, Integer> statistic) {
    private static final String STATE_TOTAL = "\n%s: %d회";

    public String getReport() {
        var stringBuilder = new StringBuilder();
        var attendanceStatusList = statistic.keySet().stream().sorted().toList();
        for (AttendanceStatus attendanceStatus : attendanceStatusList) {
            String status = attendanceStatus.getStatus();
            int count = statistic.get(attendanceStatus);
            var formatted = String.format(STATE_TOTAL, status, count);
            stringBuilder.append(formatted);
        }

        return stringBuilder.toString();
    }
}
