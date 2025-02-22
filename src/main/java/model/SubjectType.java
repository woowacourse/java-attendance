package model;

import java.util.Arrays;
import java.util.Map;

public enum SubjectType {

    경고(2),
    면담(3),
    제적(6),
    해당없음(0);

    private static final int CONVERTED_ABSENT_UNIT = 3;

    private final int threshold;

    SubjectType(final int threshold) {
        this.threshold = threshold;
    }

    public static SubjectType from(final Map<AttendanceType, Integer> result) {
        int absentCount = result.get(AttendanceType.결석);
        int lateCount = result.get(AttendanceType.지각);
        int totalAbsentCount = calculateTotalAbsentCount(lateCount, absentCount);
        return Arrays.stream(SubjectType.values())
                .sorted((subjectType1, subjectType2) -> Integer.compare(subjectType2.threshold, subjectType1.threshold))
                .filter(subjectType -> totalAbsentCount >= subjectType.threshold)
                .findFirst()
                .orElse(해당없음);
    }

    public static int calculateTotalLateCount(final int lateCount, final int absentCount) {
        return lateCount + absentCount * CONVERTED_ABSENT_UNIT;
    }

    public static int compare(final SubjectType type1, final SubjectType type2) {
        return Integer.compare(type2.threshold, type1.threshold);
    }

    public static boolean isApplicable(final Map<AttendanceType, Integer> result) {
        SubjectType subjectType = from(result);
        return !subjectType.equals(SubjectType.해당없음);
    }

    private static int calculateTotalAbsentCount(final int lateCount, final int absentCount) {
        return absentCount + lateCount / CONVERTED_ABSENT_UNIT;
    }
}
