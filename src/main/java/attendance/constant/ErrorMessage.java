package attendance.constant;

public enum ErrorMessage {
    NOT_ATTEND_DAY("[ERROR] %d월 %d일 %s은 등교일이 아닙니다."),
    NOT_OPERATING_HOURS("[ERROR] 캠퍼스 운영 시간이 아닙니다."),
    UNREGISTERED_NICKNAME("[ERROR] 캠퍼스 운영 시간이 아닙니다."),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
