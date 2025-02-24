package attendance.domain;

import java.util.Map;

public record StatusStatistic(Map<AttendanceStatus, Integer> statistic) {
    private static final String FORMAT_STATE_TOTAL = "\n%s: %d회";

    public String getReport() {
        var stringBuilder = new StringBuilder();
        var attendanceStatusList = statistic.keySet().stream().sorted().toList();
        for (AttendanceStatus attendanceStatus : attendanceStatusList) {
            String status = attendanceStatus.getStatus();
            int count = statistic.get(attendanceStatus);
            var formatted = String.format(FORMAT_STATE_TOTAL, status, count);
            stringBuilder.append(formatted);
        }

        return stringBuilder.toString();
    }

    public SanctionLevel judgeSanctionLevel() {
        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var weight = late / 3 + absence;
        return calculateSanctionLevel(weight);
    }

    private SanctionLevel calculateSanctionLevel(int weight) {
        if (weight > 5) {
            return SanctionLevel.DISMISS;
        }
        if (weight >= 3) {
            return SanctionLevel.NEED_MEETING;
        }
        if (weight > 1) {
            return SanctionLevel.WARNING;
        }
        return SanctionLevel.NONE;
    }
}
