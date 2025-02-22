package view;

public enum ErrorCode {
    CSV_INVALID_FILE_PATH("파일이 존재하지 않습니다: "),
    CSV_FILE_READING_FAIL("파일을 읽어오는 도중 오류가 발생했습니다: "),
    CSV_INVALID_FILE_TYPE("해당 디렉토리는 읽을 수 없습니다: "),

    NICKNAME_NOT_FOUND("등록되지 않은 닉네임입니다."),
    TIME_NOT_IN_OPERATION_HOUR("캠퍼스 운영 시간은 08:00~23:00 입니다."),

    SATURDAY_NOT_WORKING_DAY_FORMAT("12월 %02d일 토요일은 등교일이 아닙니다."),
    SUNDAY_NOT_WORKING_DAY_FORMAT("12월 %02d일 일요일은 등교일이 아닙니다."),
    HOLIDAY_NOT_WORKING_DAY_FORMAT("12월 %02d일 공휴일은 등교일이 아닙니다."),
    ATTENDANCE_RECORD_NOT_EXISTS_FORMAT("%02d일 기록이 존재하지 않습니다."),

    CHECK_ATTENDANCE_ALREADY_EXISTS("이미 출석한 날짜입니다. 수정 기능을 이용해주세요."),

    DAY_INPUT_NOT_VALID("날짜(일) 입력이 올바르지 않습니다."),
    TIME_INPUT_NOT_VALID("시간 입력이 올바르지 않습니다.");

    private final String format;
    private static final String ERROR_SIGN = "[ERROR] ";

    ErrorCode(String format) {
        this.format = format;
    }

    public String getFormat() {
        return ERROR_SIGN + format;
    }

    public String format(Object... args) {
        return String.format(ERROR_SIGN + format, args);
    }
}