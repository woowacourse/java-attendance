package domain;

public enum Penalty {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고");

    private final String expression;

    Penalty(String expression) {
        this.expression = expression;
    }
}
