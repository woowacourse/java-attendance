package domain;

public enum CrewPenaltyPolicy {
    WARNING,
    INTERVIEW,
    WEEDING,
    NONE;

    private static final int WEEDING_LIMIT_COUNT = 5;
    private static final int INTERVIEW_LIMIT_COUNT = 3;
    private static final int WARNING_LIMIT_COUNT = 2;
    private static final int ABSENCE_UNIT = 3;


    public static CrewPenaltyPolicy judge(int absenceCount) {
        if (absenceCount > WEEDING_LIMIT_COUNT) {
            return WEEDING;
        }

        if (absenceCount >= INTERVIEW_LIMIT_COUNT) {
            return INTERVIEW;
        }

        if (absenceCount >= WARNING_LIMIT_COUNT) {
            return WARNING;
        }

        return NONE;
    }

    public static int calculateTotalAbsence(int late, int absence) {
        int convertAbsence = late / ABSENCE_UNIT;
        return absence + convertAbsence;
    }

    public boolean isNone() {
        return this == NONE;
    }
}
