package attendance.domain.model;

import java.util.Arrays;
import java.util.Comparator;

public enum WarningLevel {

    WARNING(2),
    INTERVIEW(3),
    EXPULSION(6),
    NOT_APPLICABLE(0);

    private static final Comparator<WarningLevel> COMPARATOR = (baseType, comparedType) ->
            Integer.compare(comparedType.threshold, baseType.threshold);
    private static final int CONVERTED_ABSENT_UNIT = 3;

    private final int threshold;

    WarningLevel(final int threshold) {
        this.threshold = threshold;
    }

    public static WarningLevel from(final int absentCount, final int lateCount) {
        int totalAbsentCount = calculateTotalAbsentCount(lateCount, absentCount);
        return Arrays.stream(WarningLevel.values())
                .sorted(COMPARATOR)
                .filter(warningLevel -> totalAbsentCount >= warningLevel.threshold)
                .findFirst()
                .orElse(NOT_APPLICABLE);
    }

    public static int calculateTotalLateCount(final int lateCount, final int absentCount) {
        return lateCount + absentCount * CONVERTED_ABSENT_UNIT;
    }

    public static Comparator<WarningLevel> getComparator() {
        return COMPARATOR;
    }

    private static int calculateTotalAbsentCount(final int lateCount, final int absentCount) {
        return absentCount + lateCount / CONVERTED_ABSENT_UNIT;
    }

    public boolean isApplicable() {
        return this != WarningLevel.NOT_APPLICABLE;
    }
}
