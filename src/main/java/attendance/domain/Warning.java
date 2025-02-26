package attendance.domain;

public enum Warning {
    Warning("경고"),
    INTERVIEW("면담"),
    EXPULSION("제적"),
    NONE("");

    private final String message;

    Warning(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
