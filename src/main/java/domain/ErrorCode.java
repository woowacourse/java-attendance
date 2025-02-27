package domain;

public enum ErrorCode {
    ATTENDANCE_DATE_DUPLICATED("이미 출석한 날짜입니다. 수정 기능을 이용해주세요."),
    CREW_NAME_NOT_FOUND("등록되지 않은 닉네임입니다.");

    private static final String ERROR_PREFIX = "[ERROR]";
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
