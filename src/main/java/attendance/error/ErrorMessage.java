package attendance.error;

public enum ErrorMessage {

    NOT_OPERATING_TIME("캠퍼스 운영 시간이 아닙니다."),
    NOT_OPERATING_WEEKEND("주말은 등교일이 아닙니다."),
    NOT_OPERATING_HOLIDAY("공휴일은 운영하지 않습니다."),
    ALREADY_EXIST_ATTENDANCE("해당 날짜에 이미 출석하셨습니다."),
    NOT_EXIST_ATTENDANCE("출석 기록이 존재하지 않습니다."),
    INVALID_CREW_NAME("해당 이름을 가진 크루는 없습니다."),
    INVALID_INPUT("잘못된 입력 입니다."),
    NOT_NUMBER("숫자가 아닙니다."),
    INVALID_TIME_FORMAT("잘못된 시간 형식입니다."),
    INVALID_DATE("잘못된 날짜입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = "[ERROR] " + message;
    }

    public String getMessage() {
        return message;
    }
}
