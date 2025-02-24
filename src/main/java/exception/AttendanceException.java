package exception;

public enum AttendanceException implements ExceptionMessage {

    INVALID_ATTENDANCE_DAY("%d월 %d일 %s은 등교일이 아닙니다."),
    ;

    public final String message;

    AttendanceException(String message) {
        this.message = message;
    }

    @Override
    public String getRawMessage() {
        return message;
    }
}
