package attendance.domain;

import java.util.function.Predicate;


public enum AttendancePenalty {

    EXPULSION(calculatedAbsence -> calculatedAbsence >= Constants.EXPULSION_MINIMUM),
    COUNSELING(calculatedAbsence -> calculatedAbsence >= Constants.COUNSELING_MINIMUM
        && calculatedAbsence < Constants.EXPULSION_MINIMUM),
    WARNING(calculatedAbsence -> calculatedAbsence == Constants.WARING_MAXIMUM),
    NONE(calculatedAbsence -> calculatedAbsence < Constants.WARING_MAXIMUM);

    private final Predicate<Integer> isPenalty;

    AttendancePenalty(Predicate<Integer> isPenalty) {
        this.isPenalty = isPenalty;
    }

    public static AttendancePenalty findPenalty(int weightedLateAndAbsencePoint) {
        for (AttendancePenalty penalty : values()) {
            if (penalty.isPenalty.test(weightedLateAndAbsencePoint)) {
                return penalty;
            }
        }
        return NONE;
    }

    static class Constants {
        private static final int EXPULSION_MINIMUM = 6;
        private static final int COUNSELING_MINIMUM = 3;
        private static final int WARING_MAXIMUM = 2;
    }
}
