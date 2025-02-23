package domain;

public enum PenaltyStatus {
    NONE(""),
    EXPULSION("제적"),
    INTERVIEW("면담"),
    WARNING("경고");

    private static final int EXPULSION_COUNT = 5;
    private static final int INTERVIEW_COUNT = 3;
    private static final int WARNING_COUNT = 2;

    private final String message;

    PenaltyStatus(String message) {
        this.message = message;
    }

    public static PenaltyStatus getByPenaltyCount(int penaltyCount) {
        if (penaltyCount > EXPULSION_COUNT) {
            return EXPULSION;
        }
        if (penaltyCount >= INTERVIEW_COUNT) {
            return INTERVIEW;
        }
        if (penaltyCount >= WARNING_COUNT) {
            return WARNING;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}
