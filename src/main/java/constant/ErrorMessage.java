package constant;

public enum ErrorMessage {

    NOT_FOUND_FILE("파일을 찾을 수 없습니다."),
    FILE_READ_ERROR("파일을 읽는 중 오류가 발생했습니다."),

    INVALID_INPUT_NULL_OR_BLANK("입력 값은 null이거나 공백일 수 없습니다."),
    INVALID_INTEGER_FORMAT("해당 입력 값은 정수이어야 합니다."),
    INVALID_TIME_FORMAT("해당 입력 값은 시간 형식이어야 합니다. (ex. 09:24)"),
    INVALID_DAY_FORMAT("이번 달의 유효한 날짜를 입력해 주세요."),
    INVALID_OPTION_FORMAT("유효한 옵션이 아닙니다."),

    CANNOT_CHECK_IN_ON_WEEKEND("주말에는 출석할 수 없습니다."),
    ;

    private static final String prefix = "[ERROR] ";
    private static final String newLine = "\n";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return prefix + message + newLine;
    }
}
