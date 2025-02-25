package attendance.domain;

import java.util.Arrays;

public enum AttendanceRisk {
    EXPULSION(6),
    COUNSELING(3),
    WARNING(2),
    NONE(0);

    private final int threshold;

    AttendanceRisk(final int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceRisk find(final int absence, final int late) {
        int allAbsence = absence + (late / 3);
        return Arrays.stream(values())
                .filter(type -> type.threshold <= allAbsence)
                .findFirst()
                .orElse(NONE);
    }
}
