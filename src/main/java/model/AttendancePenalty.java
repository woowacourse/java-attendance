package model;

public enum AttendancePenalty {
    NONE("없음", 0),
    WARNING("경고", 2),
    COUNSELING("면담", 3),
    EXPULSION("제적", 5);

    private final String penalty;
    private final int penaltyCount;

    AttendancePenalty(String penalty, int penaltyCount) {
        this.penalty = penalty;
        this.penaltyCount = penaltyCount;
    }

    public static AttendancePenalty findPenaltyByAbsentCount(long count) {
        if (count > AttendancePenalty.EXPULSION.penaltyCount) {
            return AttendancePenalty.EXPULSION;
        }
        if (count >= AttendancePenalty.COUNSELING.penaltyCount) {
            return AttendancePenalty.COUNSELING;
        }
        if (count >= AttendancePenalty.WARNING.penaltyCount) {
            return AttendancePenalty.WARNING;
        }
        return NONE;
    }

    public String getPenalty() {
        return penalty;
    }
}
