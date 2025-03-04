package domain;

import java.util.Arrays;

public enum Penalty {
    EXPELLED("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    PASS("패스", 0);

    private final String name;
    private final int count;

    Penalty(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public static Penalty of(int lateCount, int absentCount) {
        int totalAbsences = absentCount + lateCount / 3;
        return Arrays.stream(values())
            .filter(penalty -> totalAbsences >= penalty.count)
            .findFirst()
            .orElse(PASS);
    }

    public static boolean isNotPass(int lateCount, int absentCount) {
        Penalty penalty = of(lateCount, absentCount);
        return penalty != PASS;
    }

    public String getName() {
        return name;
    }
}