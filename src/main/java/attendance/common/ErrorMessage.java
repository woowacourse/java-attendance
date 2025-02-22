package attendance.common;

public enum ErrorMessage {

    INVALID_OPTION_INPUT("기능은 1,2,3,4,Q만 입력 가능합니다"),
    NOT_OPEN_TIME("운영시간은 08 ~ 23시 까지 입니다."),
    NO_NAME("등록되지 않은 닉네임입니다."),
    NO_ATTENDANCE_RECORD("출석 기록을 찾을 수 없습니다."),
    INVALID_DATE("출석기록하는 달이 아닙니다."),
    NOT_OPEN_DAY("%s은 등교일이 아닙니다."),
    INVALID_TIME_FORMAT_INPUT("HH:mm 형식을 지켜 작성해주세요."),
    INVALID_FORMAT("올바른 입력 형식이 아닙니다."),
    FILE_READ_FAIL("파일 읽기에 실패했습니다. 파일경로 : %s"),
    INVALID_FILE_FORMAT("출석 파일 입력 형식이 올바르지 않습니다.");

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String formatMessage(String args) {
        return PREFIX + String.format(message, args);
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
