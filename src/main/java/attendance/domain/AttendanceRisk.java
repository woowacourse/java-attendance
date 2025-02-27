package attendance.domain;

import java.util.Arrays;

public enum AttendanceRisk {
    WEEDING(6),
    INTERVIEW(3),
    WARNING(2),
    NONE(Integer.MIN_VALUE);

    private final int threshold;

    AttendanceRisk(final int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceRisk evaluate(final int absence, final int tardy) {
        return Arrays.stream(values())
                .filter(type -> type.threshold <= calculateAllAbsence(absence, tardy))
                .findFirst()
                .orElse(NONE);
    }

    private static int calculateAllAbsence(final int absence, final int tardy) {
        return absence + tardy / 3;
    }
}
