package attendance.domain;

import java.util.Comparator;
import java.util.EnumMap;

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

    @Override
    public int compareTo(HistoryStatistic o) {
        return Comparator.comparing(HistoryStatistic::judgeSanctionLevel)
            .thenComparing(HistoryStatistic::getWeightForComparingSort, Comparator.reverseOrder())
            .thenComparing(HistoryStatistic::nickname)
            .compare(this, o);
    }
}
