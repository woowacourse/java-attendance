package exception;

public enum AttendanceDateTimeExceptionType implements ExceptionMessage {

    INVALID_DATE_TIME_TYPE("형식은 yyyy-MM-dd HH:mm 입니다."),
    INVALID_TIME_TYPE("시간은 HH:mm 형식으로 들어와야 합니다."),
    ;

    private final String message;

    AttendanceDateTimeExceptionType(String message) {
        this.message = message;
    }

    @Override
    public String getRawMessage() {
        return message;
    }
}
