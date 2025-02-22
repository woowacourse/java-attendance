package domain.constants;

public enum ErrorMessage {
    USER_COMMAND_NOT_FOUND("존재하지 않는 명령어입니다. 다시 입력하세요.");

    private static final String ERROR_FORMAT = "[ERROR] %s";
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format(ERROR_FORMAT, message);
    }
}
