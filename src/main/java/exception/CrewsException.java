package exception;

public enum CrewsException implements ExceptionMessage {

    INVALID_EXIST_CREW("크루가 존재하지 않습니다."),
    ;

    public final String message;

    CrewsException(String message) {
        this.message = message;
    }
    @Override
    public String getRawMessage() {
        return message;
    }
}