package domain;

public enum Penalty {
    WARNING,
    INTERVIEW,
    REMOVAL,
    NONE;

    public static Penalty determine(int absenceCount, int lateCount) {
        absenceCount = absenceCount + lateCount / 3;
        if (absenceCount > 5) {
            return Penalty.REMOVAL;
        }
        if (absenceCount >= 3) {
            return Penalty.INTERVIEW;
        }
        if (absenceCount >= 2) {
            return Penalty.WARNING;
        }
        return Penalty.NONE;
    }

}
