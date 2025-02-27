package attendance.constant;

public enum ErrorMessage {
    INVALID_ATTEND_DATE("[ERROR] %s월 %s일 %s은 등교일이 아닙니다."),
    UNREGISTERED_NICKNAME("[ERROR] 등록되지 않은 닉네임입니다."),
    ;

    private final String message;


    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
