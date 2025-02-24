package attendance.exception;

public enum ExceptionMessage {
    NOT_CAMPUS_TIME("[ERROR] 캠퍼스 운영시간이 아닙니다."),
    NOT_FOUND_CREW("[ERROR] 등록되지 않은 닉네임입니다."),
    HOLIDAY("[ERROR] %s월 %s일 %s은 등교일이 아닙니다."),
    ALREADY_EXIST_HOLIDAY("[ERROR] 이미 추가된 휴일입니다."),
    INVALID_DATE_TIME_FORMAT("[ERROR] 잘못된 날짜와 시간 형식입니다."),
    INVALID_DATE_FORMAT("[ERROR] 잘못된 날짜 형식입니다."),
    INVALID_TIME_FORMAT("[ERROR] 잘못된 시간 형식입니다."),
    NOT_NUMERIC_INPUT("[ERROR] 숫자만 입력 가능합니다."),
    ALREADY_ATTENDANCE("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요."),
    INVALID_MENU_COMMAND("[ERROR] 메뉴의 알맞은 커맨드를 입력해주세요.");


    private final String content;

    ExceptionMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
