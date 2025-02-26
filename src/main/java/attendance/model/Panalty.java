package attendance.model;

import java.util.Arrays;
import java.util.Comparator;

public enum Panalty {
    NONE(0),
    WARN(2),
    INTERVIEW(3),
    DISMISSAL(6),
    ;
    private final int absenceCount;

    Panalty(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static Panalty of(long absenceCount, long lateCount) {
        long totalAbsenceCount = absenceCount + lateCount / 3;
        return Arrays.stream(Panalty.values())
                .sorted(Comparator.reverseOrder())
                .filter(panalty -> panalty.absenceCount <= totalAbsenceCount)
                .findFirst()
                .orElse(NONE);
    }
}
