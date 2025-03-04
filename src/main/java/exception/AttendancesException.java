package exception;

public enum AttendancesException implements ExceptionMessage {

    NOT_FOUND_DAY_OF_MONTH("수정하는 일자를 찾을 수 없습니다."),
    ;

    public final String message;

    AttendancesException(String message) {
        this.message = message;
    }
    @Override
    public String getRawMessage() {
        return message;
    }
}
