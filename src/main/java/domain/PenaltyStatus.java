package domain;

public enum PenaltyStatus {
    NONE(""),
    WARNING("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적");

    private final String message;

    PenaltyStatus(String message) {
        this.message = message;
    }

    public static PenaltyStatus getByPenaltyCount(int penaltyCount) {
        if (penaltyCount > 5) {
            return EXPULSION;
        }
        if (penaltyCount >= 3) {
            return INTERVIEW;
        }
        if (penaltyCount >= 2) {
            return WARNING;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}
