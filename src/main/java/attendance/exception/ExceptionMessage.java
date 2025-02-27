package attendance.exception;

public enum ExceptionMessage {
    ALREADY_ATTENDANCE("[ERROR] 이미 출석을 완료했습니다! 수정 기능을 활용해주세요!"),
    INVALID_CREW("[ERROR] 등록되지 않은 닉네임입니다."),
    HOLIDAY_ATTENDANCE("[ERROR] %s월 %s일 %S요일은 등교일이 아닙니다."),
    OUT_OF_CAMPUS_TIME("[ERROR] 캠퍼스 운영 시간이 아닙니다."),
    INCORRECT_MENU("[ERROR] 잘못된 메뉴가 선택되었습니다. 메뉴를 다시 입력해주세요!"),
    NOT_FOUND_FILE("[ERROR] 파일을 찾을 수 없습니다."),
    FILE_IO_ERROR("[ERROR] 파일을 읽는 도중 오류가 발생했습니다."),
    TIME_FORMAT_ERROR("[ERROR] 지원하지 않는 시간형식이 입력되었습니다."),
    NOT_NUMERIC_INPUT("[ERROR] 숫자가 아닌 형식의 데이터가 입력되었습니다."),
    BLANK_INPUT("[ERROR] 비어있는 값이 입력되었습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
