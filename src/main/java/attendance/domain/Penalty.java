package attendance.domain;

public enum Penalty {
    REMOVAL("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE(null);

    private static final int REMOVAL_THRESHOLD = 5;
    private static final int INTERVIEW_THRESHOLD = 2;
    private static final int WARNING_THRESHOLD = 1;

    private static final int LATE_TO_ABSENCE_RATIO = 3;

    private final String name;

    Penalty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Penalty determine(int absenceCount, int lateCount) {
        absenceCount = absenceCount + lateCount / LATE_TO_ABSENCE_RATIO;
        if (absenceCount > REMOVAL_THRESHOLD) {
            return Penalty.REMOVAL;
        }
        if (absenceCount > INTERVIEW_THRESHOLD) {
            return Penalty.INTERVIEW;
        }
        if (absenceCount > WARNING_THRESHOLD) {
            return Penalty.WARNING;
        }
        return Penalty.NONE;
    }
}
