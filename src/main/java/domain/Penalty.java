package domain;

public enum Penalty {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    NONE("");

    private final String message;

    Penalty(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
