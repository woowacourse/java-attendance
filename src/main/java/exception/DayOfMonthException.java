package exception;

public enum DayOfMonthException implements ExceptionMessage {

    INVALID_DAY_RANGE("일은 1부터 %d사이의 숫자만 가능합니다."),
    ;

    public final String message;

    DayOfMonthException(String message) {
        this.message = message;
    }

    @Override
    public String getRawMessage() {
        return message;
    }
}