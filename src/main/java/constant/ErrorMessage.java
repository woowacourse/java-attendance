package constant;

public enum ErrorMessage {

    INVALID_INPUT_NULL_OR_BLANK("입력 값은 null이거나 공백일 수 없습니다."),
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
