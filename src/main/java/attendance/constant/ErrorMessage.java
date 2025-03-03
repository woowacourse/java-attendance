package attendance.constant;

public enum ErrorMessage {
    INVALID_ATTEND_DATE("[ERROR] %s월 %s일 %s은 등교일이 아닙니다."),
    UNREGISTERED_NICKNAME("[ERROR] 등록되지 않은 닉네임입니다."),
    ALREADY_ATTEND("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해주세요."),
    DATE_WITHOUT_ATTENDANCE("[ERROR] 출석하지 않은 날짜입니다. 출석한 날짜를 수정해주세요."),
    ;

    private final String message;


    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
