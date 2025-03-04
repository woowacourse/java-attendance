package exception;

public enum CommandException implements ExceptionMessage {

    INVALID_FORMAT("알맞은 명령어를 입력하세요."),
            ;

    public final String message;

    CommandException(String message) {
        this.message = message;
    }
    @Override
    public String getRawMessage() {
        return message;
    }
}

