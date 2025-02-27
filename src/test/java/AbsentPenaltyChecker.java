public class AbsentPenaltyChecker {

    public AbsentPenalty check(int absentCount) {
        if (absentCount >= 3) {
            return AbsentPenalty.INTERVIEW;
        }
        return AbsentPenalty.WARNING;
    }
}
