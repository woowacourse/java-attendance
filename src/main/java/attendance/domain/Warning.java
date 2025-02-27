package attendance.domain;

public enum Warning {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    Warning("경고", 2),
    NONE("", 0);

    private final String message;
    private final int criteria;

    Warning(String message, int criteria) {
        this.message = message;
        this.criteria = criteria;
    }

    public static Warning from(final int value) {
        if (value > EXPULSION.criteria) {
            return EXPULSION;
        }
        if (value >= INTERVIEW.criteria) {
            return INTERVIEW;
        }
        if (value >= Warning.criteria) {
            return Warning;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}
