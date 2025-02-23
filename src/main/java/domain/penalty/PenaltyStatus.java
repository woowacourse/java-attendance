package domain.penalty;

public enum PenaltyStatus {

    WARNING(2),
    INTERVIEWEE(3),
    EXPULSION(6),
    NONE(0);

    public static final int LATE_TO_ABSENCE_UNIT = 3;

    private final int penalty;

    PenaltyStatus(int penalty) {
        this.penalty = penalty;
    }

    public static PenaltyStatus getPenaltyStatus(int absenceCount, int lateCount) {
        int penaltyCount = convertToAbsence(absenceCount, lateCount);

        if (penaltyCount < WARNING.penalty) {
            return NONE;
        }
        if (penaltyCount < INTERVIEWEE.penalty) {
            return WARNING;
        }
        if (penaltyCount < EXPULSION.penalty) {
            return INTERVIEWEE;
        }
        return EXPULSION;
    }

    private static int convertToAbsence(int absenceCount, int lateCount) {
        return (lateCount / LATE_TO_ABSENCE_UNIT) + absenceCount;
    }
}
