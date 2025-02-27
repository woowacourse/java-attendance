package attendance.domain.model;

import java.util.Arrays;
import java.util.Comparator;

public enum SubjectType {

    WARNING(2),
    INTERVIEW(3),
    EXPULSION(6),
    NOT_APPLICABLE(0);

    private static final Comparator<SubjectType> COMPARATOR = (baseType, comparedType) ->
            Integer.compare(comparedType.threshold, baseType.threshold);
    private static final int CONVERTED_ABSENT_UNIT = 3;

    private final int threshold;

    SubjectType(final int threshold) {
        this.threshold = threshold;
    }

    public static SubjectType from(final int absentCount, final int lateCount) {
        int totalAbsentCount = calculateTotalAbsentCount(lateCount, absentCount);
        return Arrays.stream(SubjectType.values())
                .sorted(COMPARATOR)
                .filter(subjectType -> totalAbsentCount >= subjectType.threshold)
                .findFirst()
                .orElse(NOT_APPLICABLE);
    }

    public static int calculateTotalLateCount(final int lateCount, final int absentCount) {
        return lateCount + absentCount * CONVERTED_ABSENT_UNIT;
    }

    public static Comparator<SubjectType> getComparator() {
        return COMPARATOR;
    }

    private static int calculateTotalAbsentCount(final int lateCount, final int absentCount) {
        return absentCount + lateCount / CONVERTED_ABSENT_UNIT;
    }

    public boolean isApplicable() {
        return this != SubjectType.NOT_APPLICABLE;
    }
}
