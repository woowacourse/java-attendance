package attendance.exception;

public enum ErrorMessage {

    DAY_OUT_OF_RANGE("유효한 날짜 범위가 아닙니다."),
    COLON_ERROR("시간:분 형식으로 입력하셔야 합니다."),
    FILE_NOT_PRESENCE("파일이 존재하지 않습니다."),
    FUNCTION_NOT_PRESENCE("선택하신 기능은 없는 기능입니다."),
    NUMBER_FORMAT_HOUR_ERROR("시간은 숫자로 입력하셔야 합니다."),
    NICKNAME_NOT_PRESENCE("없는 닉네임 입니다."),
    NUMBER_FORMAT_MINUTE_ERROR("분은 숫자로 입력하셔야 합니다."),
    NOT_RISK_CREW("제적사항이 없는 학생입니다."),
    NUMBER_FORMAT_ERROR("숫자로 입력하셔야 합니다."),
    CAMPUS_NOT_OPERATION("캠퍼스 운영 시간은 08시부터 23시까지 입니다.");

    private final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return this.message;
    }

}