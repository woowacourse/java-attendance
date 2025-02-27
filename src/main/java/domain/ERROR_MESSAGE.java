package domain;

public enum ERROR_MESSAGE {
    CLOSED_DAY("오늘은 캠퍼스 휴장일입니다."),
    CLOSED_TIME("지금은 캠퍼스 휴장시간입니다.");

    private final String message;

    ERROR_MESSAGE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
