package domain;

import static constants.NumberConstants.EXPULSION_COUNT;
import static constants.NumberConstants.INTERVIEW_COUNT;
import static constants.NumberConstants.WARNING_COUNT;

public enum PenaltyStatus {
    NONE(""),
    EXPULSION("제적"),
    INTERVIEW("면담"),
    WARNING("경고");

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
