package attendance.domain;

import java.util.Arrays;

public enum AttendanceRisk {
    WEEDING(6, Integer.MAX_VALUE),
    INTERVIEW(3, 5),
    WARNING(2, 2),
    NONE(Integer.MIN_VALUE, 1);

    private final int minThreshold;
    private final int maxThreshold;

    AttendanceRisk(final int minThreshold, final int maxThreshold) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public static AttendanceRisk evaluate(final int absence, final int tardy) {
        int allAbsence = calculateAllAbsence(absence, tardy);
        return Arrays.stream(values())
                .filter(type -> type.minThreshold <= allAbsence && type.maxThreshold >= allAbsence)
                .findFirst()
                .orElse(NONE);
    }

    private static int calculateAllAbsence(final int absence, final int tardy) {
        return absence + tardy / 3;
    }
}
