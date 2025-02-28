package attendance.domain;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

public record HistoryStatistic(EnumMap<AttendanceStatus, Integer> statistic, String nickname)
    implements Comparable<HistoryStatistic> {
    public static final int DIVIDER = 3;

    public SanctionLevel judgeSanctionLevel() {
        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var weight = late / DIVIDER + absence;

        return SanctionLevel.getValueByWight(weight);
    }

    public int getWeightForComparingSort() {
        return statistic.getOrDefault(AttendanceStatus.ABSENCE, 0)
            + statistic.getOrDefault(AttendanceStatus.LATE, 0);
    }

    public List<AttendanceStatus> getStatusesReverseOrder() {
        return statistic.keySet().stream().sorted(Comparator.reverseOrder()).toList();
    }

    @Override
    public int compareTo(HistoryStatistic o) {
        return Comparator.comparing(HistoryStatistic::judgeSanctionLevel)
            .thenComparing(HistoryStatistic::getWeightForComparingSort, Comparator.reverseOrder())
            .thenComparing(HistoryStatistic::nickname)
            .compare(this, o);
    }

    public int getOrDefault(AttendanceStatus status, int defaultInt) {
        return statistic.getOrDefault(status, defaultInt);
    }
}
