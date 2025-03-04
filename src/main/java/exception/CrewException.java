package exception;

public enum CrewException implements ExceptionMessage {

    ALREADY_ATTENDANCE("이미 출석했습니다. 다음에는 수정기능을 이용해주세요."),
    ;

    public final String message;

    CrewException(String message) {
        this.message = message;
    }
    @Override
    public String getRawMessage() {
        return message;
    }
}