package domain;

import java.util.Arrays;

public enum Penalty {
    EXPELLED("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    PASS("패스", 0);

    private final String penalty;
    private final int count;

    Penalty(String penalty, int count) {
        this.penalty = penalty;
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
        // TODO: 패스인지 확인
        return false;
    }

    public String getPenalty() {
        return penalty;
    }
}