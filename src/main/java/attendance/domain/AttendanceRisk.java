package attendance.domain;

import java.util.Arrays;

public enum AttendanceRisk {
    INTERVIEW(3),
    WARNING(2);

    private final int threshold;

    AttendanceRisk(final int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceRisk evaluate(final int absence) {
        return Arrays.stream(values())
                .filter(type -> type.threshold <= absence)
                .findFirst()
                .orElse(null);
    }
}
