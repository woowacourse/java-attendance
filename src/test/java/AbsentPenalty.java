public enum AbsentPenalty {
    WARNING(2),
    INTERVIEW(3),
    DISMISSAL(6),
    NONE(0),
    ;

    private final int criterionAbsentCount;

    AbsentPenalty(int criterionAbsentCount) {
        this.criterionAbsentCount = criterionAbsentCount;
    }

    public static AbsentPenalty determine(int absentCount) {
        if (absentCount >= DISMISSAL.criterionAbsentCount) {
            return AbsentPenalty.DISMISSAL;
        }

        if (absentCount >= INTERVIEW.criterionAbsentCount) {
            return AbsentPenalty.INTERVIEW;
        }

        if (absentCount == WARNING.criterionAbsentCount) {
            return AbsentPenalty.WARNING;
        }

        return AbsentPenalty.NONE;
    }
}
