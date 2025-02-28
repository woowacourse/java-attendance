package domain;

public enum DangerousTarget {
    WARNING(2, "경고"),
    ONE_ON_ONE(3, "면담"),
    DISMISSAL(5, "제적"),
    SAFE(0, "안전");

    public static final int LateRateOfAbsent = 3;

    private final int absentCount;
    private final String target;

    DangerousTarget(int absentCount, String target) {
        this.absentCount = absentCount;
        this.target = target;
    }

    public static DangerousTarget getWarningStatus(int lateCount, int absentCount) {
        int count = lateCount / LateRateOfAbsent + absentCount;
        if (count > DISMISSAL.absentCount)
            return DISMISSAL;
        if (count >= ONE_ON_ONE.absentCount)
            return ONE_ON_ONE;
        if (count >= WARNING.absentCount)
            return WARNING;
        return SAFE;
    }

    public String getTarget() {
        return target;
    }
}
