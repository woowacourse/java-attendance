package attendance.domain;

public enum Penalty {
    REMOVAL("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE(null);

    private final String name;

    Penalty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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
