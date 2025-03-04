package exception;

public enum NickNameException implements ExceptionMessage {

    INVALID_NICKNAME_FORMAT("%d월 %d일 %s은 등교일이 아닙니다."),
    ;

    public final String message;

    NickNameException(String message) {
        this.message = message;
    }

    @Override
    public String getRawMessage() {
        return message;
    }
}