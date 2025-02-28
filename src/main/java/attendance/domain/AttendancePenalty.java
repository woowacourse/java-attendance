package attendance.domain;

import java.util.Map;

public enum AttendancePenalty {
    NONE("없음", 0),
    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    WEEDING("제적", 5);

    private final String title;
    private final int threshold;

    AttendancePenalty(
        final String title,
        final int threshold
    ) {
        this.title = title;
        this.threshold = threshold;
    }

    public static AttendancePenalty from(final Map<AttendanceStatus, Integer> attendanceStatusCount) {
        final int absenceCount = calculateAbsenceCount(attendanceStatusCount);

        return calculatePenalty(absenceCount);
    }

    public static int calculateAbsenceCount(final Map<AttendanceStatus, Integer> attendanceStatusCount) {
        final int lateCount = attendanceStatusCount.getOrDefault(
            AttendanceStatus.LATE, 0);
        int absenceCount = attendanceStatusCount.getOrDefault(
            AttendanceStatus.ABSENCE, 0);

        absenceCount += (lateCount / 3);

        return absenceCount;
    }

    private static AttendancePenalty calculatePenalty(final int absenceCount) {
        if (absenceCount > WEEDING.threshold) {
            return WEEDING;
        }

        return NONE;
    }
}
