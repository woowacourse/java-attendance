package attendance.exception;

public enum ErrorMessage {

    DAY_OUT_OF_RANGE("유효한 날짜 범위가 아닙니다."),
    SEPARATE_WITH_COLON_ERROR("시:분 형식으로 입력해 주세요."),
    FILE_NOT_PRESENCE("파일이 존재하지 않습니다."),
    FUNCTION_NOT_PRESENCE("선택하신 기능은 없는 기능입니다."),
    TIME_FORMAT_ERROR("시 및 분은 숫자로 입력하셔야 합니다."),
    NICKNAME_NOT_PRESENCE("없는 닉네임 입니다."),
    NOT_RISK_CREW("제적사항이 없는 학생입니다."),
    OUT_OF_CAMPUS_TIME_RANGE("캠퍼스 운영 시간은 매일 08:00 ~ 23:00 입니다"),
    OUT_OF_HOUR_RANGE("24시 이내로 입력해 주세요"),
    OUT_OF_MINUTE_RANGE("60분 이내로 입력해 주세요")
    ;

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return this.message;
    }
}
