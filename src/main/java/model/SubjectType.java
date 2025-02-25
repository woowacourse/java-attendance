package model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum SubjectType {

    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    EXPULSION("제적", 6),
    NOT_APPLICABLE("해당없음", 0);

    private static final Comparator<SubjectType> COMPARATOR = (baseType, comparedType) ->
            Integer.compare(comparedType.threshold, baseType.threshold);
    private static final int CONVERTED_ABSENT_UNIT = 3;

    private final String name;
    private final int threshold;

    SubjectType(final String name, final int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static SubjectType from(final Map<AttendanceType, Integer> result) {
        int absentCount = result.get(AttendanceType.ABSENCE);
        int lateCount = result.get(AttendanceType.LATE);
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

    public static boolean isApplicable(final Map<AttendanceType, Integer> result) {
        SubjectType subjectType = from(result);
        return !subjectType.equals(SubjectType.NOT_APPLICABLE);
    }

    public static Comparator<SubjectType> getComparator() {
        return COMPARATOR;
    }

    private static int calculateTotalAbsentCount(final int lateCount, final int absentCount) {
        return absentCount + lateCount / CONVERTED_ABSENT_UNIT;
    }

    public String getName() {
        return name;
    }
}
