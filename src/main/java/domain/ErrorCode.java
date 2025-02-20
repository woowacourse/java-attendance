package domain;

public enum ErrorCode {
    TIME_NOT_IN_OPERATION_HOUR("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다."),
    NICKNAME_NOT_FOUND("[ERROR] 등록되지 않은 닉네임입니다."),
    SATURDAY_NOT_WORKING_DAY_FORMAT("[ERROR] 12월 %02d일 토요일은 등교일이 아닙니다."),
    SUNDAY_NOT_WORKING_DAY_FORMAT("[ERROR] 12월 %02d일 일요일은 등교일이 아닙니다."),
    HOLIDAY_NOT_WORKING_DAY_FORMAT("[ERROR] 12월 %02d일 공휴일은 등교일이 아닙니다."),
    CHECK_ATTENDANCE_ALREADY_EXISTS("[ERROR] 이미 출석한 날짜입니다. 수정 기능을 이용해주세요."),
    ATTENDANCE_RECORD_NOT_EXISTS_FORMAT("[ERROR] %02d일 기록이 존재하지 않습니다."),
    CSV_INVALID_FILE_PATH("[ERROR] 파일이 존재하지 않습니다: "),
    CSV_FILE_READING_FAIL("[ERROR] 파일을 읽어오는 도중 오류가 발생했습니다: "),
    CSV_INVALID_FILE_TYPE("[ERROR] 해당 디렉토리는 읽을 수 없습니다: "),
    DAY_INPUT_NOT_VALID("[ERROR] 날짜(일) 입력이 올바르지 않습니다."),
    TIME_INPUT_NOT_VALID("[ERROR] 시간 입력이 올바르지 않습니다."),
    INPUT_NOT_VALID("[ERROR] 입력이 올바르지 않습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
