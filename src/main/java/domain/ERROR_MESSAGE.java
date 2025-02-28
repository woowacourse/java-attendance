package domain;

public enum ERROR_MESSAGE {
    CLOSED_DAY("오늘은 캠퍼스 휴장일입니다."),
    CLOSED_TIME("지금은 캠퍼스 휴장시간입니다."),
    ALREADY_ATTENDED("이미 출석하였습니다. 수정을 원하시면 출석 수정 기능을 이용하세요."),
    EMPTY_DATE("등록되지 않은 날짜입니다.")
    ;

    private final String message;

    ERROR_MESSAGE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
