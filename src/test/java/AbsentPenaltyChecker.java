public class AbsentPenaltyChecker {

    public AbsentPenalty determine(int absentCount) {
        if (absentCount > 5) {
            return AbsentPenalty.DISMISSAL;
        }

        if (absentCount >= 3) {
            return AbsentPenalty.INTERVIEW;
        }

        if (absentCount == 2) {
            return AbsentPenalty.WARNING;
        }

        return AbsentPenalty.NONE;
    }
}
