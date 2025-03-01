package constant;

public enum ErrorMessage {

    INVALID_INPUT_NULL_OR_BLANK("입력 값은 null이거나 공백일 수 없습니다."),
    INVALID_INTEGER_FORMAT("해당 입력 값은 정수이어야 합니다."),
    INVALID_TIME_FORMAT("해당 입력 값은 시간 형식이어야 합니다. (ex. 09:24)"),
    ;

    private static final String prefix = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return prefix + message;
    }
}
