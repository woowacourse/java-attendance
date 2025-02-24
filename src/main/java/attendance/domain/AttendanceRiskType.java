package attendance.domain;

import java.util.Arrays;

public enum AttendanceRiskType {
    EXPULSION("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    NONE("해당없음", 0);

    private final String name;
    private final int threshold;

    AttendanceRiskType(final String name, final int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static AttendanceRiskType find(final int absence, final int late) {
        int allAbsence = absence + (late / 3);
        return Arrays.stream(values())
                .filter(type -> type.threshold <= allAbsence)
                .findFirst()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }
}
