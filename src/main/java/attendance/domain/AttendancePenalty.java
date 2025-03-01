package attendance.domain;

public enum AttendancePenalty {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    CAUTION("경고", 2),
    NONE("", 0);

    private final String message;
    private final int criteria;

    AttendancePenalty(String message, int criteria) {
        this.message = message;
        this.criteria = criteria;
    }

    public static AttendancePenalty from(final int value) {
        if (value > EXPULSION.criteria) {
            return EXPULSION;
        }
        if (value >= INTERVIEW.criteria) {
            return INTERVIEW;
        }
        if (value >= CAUTION.criteria) {
            return CAUTION;
        }
        return NONE;
    }

    public String getMessage() {
        return message;
    }
}
