package domain;

public enum PenaltyStatus {
    WARNING(2),
    INTERVIEWEE(3),
    EXPULSION(6),
    NONE(0);

    private final int penalty;

    PenaltyStatus(int penalty) {
        this.penalty = penalty;
    }

    public static PenaltyStatus getPenaltyStatus(int absenceCount, int lateCount) {
        int penaltyCount = convertToAbsence(absenceCount, lateCount);

        if (penaltyCount < 2) {
            return PenaltyStatus.NONE;
        }
        if (penaltyCount < 3) {
            return PenaltyStatus.WARNING;
        }
        if (penaltyCount < 6) {
            return PenaltyStatus.INTERVIEWEE;
        }
        return PenaltyStatus.EXPULSION;
    }

    private static int convertToAbsence(int absenceCount, int lateCount) {
        return (lateCount / 3) + absenceCount;
    }
}
