package domain;

import static util.Constants.*;

public enum Penalty {
    EXPULSION("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE("비대상자");

    private final String message;

    Penalty(String message) {
        this.message = message;
    }

    public static Penalty from(int absentCount) {
        if (absentCount > EXPULSION_COUNT) {
            return EXPULSION;
        }
        if (absentCount >= INTERVIEW_COUNT) {
            return INTERVIEW;
        }
        if (absentCount == WARNING_COUNT) {
            return WARNING;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}
