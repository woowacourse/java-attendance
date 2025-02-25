package attendance.exception;

public enum ExceptionMessage {
    ALREADY_ATTENDANCE("[ERROR] 이미 출석을 완료했습니다! 수정 기능을 활용해주세요!"),
    INVALID_CREW("[ERROR] 등록되지 않은 닉네임입니다."),
    HOLIDAY_ATTENDANCE("[ERROR] %s월 %s일 %S요일은 등교일이 아닙니다."),
    OUT_OF_CAMPUS_TIME("[ERROR] 캠퍼스 운영 시간이 아닙니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
