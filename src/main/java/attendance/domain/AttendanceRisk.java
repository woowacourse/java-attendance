package attendance.domain;

import java.util.Arrays;

public enum AttendanceRisk {
    WEEDING(6),
    INTERVIEW(3),
    WARNING(2),
    NONE(Integer.MIN_VALUE); // 임계값을 특별히 정의하지 않았습니다. (Integer.MIN_VALUE 사용)

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
