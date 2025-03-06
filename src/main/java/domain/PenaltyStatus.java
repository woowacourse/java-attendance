package domain;

public enum PenaltyStatus {
    WARNING(2),
    INTERVIEW(3),
    EXPULSION(6),
    NONE(0),
    ;

    private final int absenceCountLimit;

    private static final int LATE_TO_ABSENCE_RATIO = 3;

    PenaltyStatus(int absenceCountLimit) {
        this.absenceCountLimit = absenceCountLimit;
    }

    public static PenaltyStatus determinePenalty(int lateCount, int absenceCount) {
        int convertedAbsenceCount = convertLateToAbsence(lateCount, absenceCount);
        if (convertedAbsenceCount >= EXPULSION.absenceCountLimit) {
            return EXPULSION;
        }
        if (convertedAbsenceCount >= INTERVIEW.absenceCountLimit) {
            return INTERVIEW;
        }
        if (convertedAbsenceCount >= WARNING.absenceCountLimit) {
            return WARNING;
        }
        return NONE;
    }

    public static int convertAbsenceToLate(int lateCount, int absenceCount) {
        return lateCount + (absenceCount * LATE_TO_ABSENCE_RATIO);
    }

    private static int convertLateToAbsence(int lateCount, int absenceCount) {
        return absenceCount + (lateCount / LATE_TO_ABSENCE_RATIO);
    }
}
