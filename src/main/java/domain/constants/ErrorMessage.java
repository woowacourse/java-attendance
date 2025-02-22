package domain.constants;

public enum ErrorMessage {
    USER_COMMAND_NOT_FOUND("존재하지 않는 명령어입니다. 다시 입력하세요."),
    CREW_NOT_FOUND("크루가 존재하지 않습니다."),
    INVALID_DATE("유효하지 않은 날짜입니다.");

    private static final String ERROR_FORMAT = "[ERROR] %s";
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format(ERROR_FORMAT, message);
    }
}
