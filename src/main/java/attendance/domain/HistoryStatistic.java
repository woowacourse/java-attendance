package attendance.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.function.Predicate;

public record HistoryStatistic(EnumMap<AttendanceStatus, Integer> statistic, String nickname)
    implements Comparable<HistoryStatistic> {
    private static final String FORMAT_STATE = "%s: %d회\n";
    private static final String FORMAT_SANCTION = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    public static final int DIVIDER = 3;

    public SanctionLevel judgeSanctionLevel() {
        int late = statistic.getOrDefault(AttendanceStatus.LATE, 0);
        int absence = statistic.getOrDefault(AttendanceStatus.ABSENCE, 0);
        var weight = late / DIVIDER + absence;
        return SanctionLevel.getByWight(weight);
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

    public enum SanctionLevel {
        DISMISS("제적", weight -> weight > 5),
        NEED_MEETING("면담", weight -> weight >= 3),
        WARNING("경고", weight -> weight > 1),
        NONE("", weight -> weight <= 1),
        ;

        public String getValues() {
            return value;
        }

        private final Predicate<Integer> condition;
        private final String value;

        SanctionLevel(String value, Predicate<Integer> condition) {
            this.value = value;
            this.condition = condition;
        }

        public boolean matches(int wight) {
            return condition.test(wight);
        }

        public static SanctionLevel getByWight(int wight) {
            return Arrays.stream(values())
                .filter(status -> status.matches(wight))
                .findFirst()
                .orElse(NONE);
        }
    }
}
