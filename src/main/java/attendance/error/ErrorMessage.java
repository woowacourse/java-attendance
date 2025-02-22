package attendance.error;

public enum ErrorMessage {

    NOT_OPERATING_TIME("캠퍼스 운영 시간이 아닙니다."),
    NOT_OPERATING_WEEKEND("주말은 등교일이 아닙니다."),
    NOT_OPERATING_HOLIDAY("공휴일은 운영하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = "[ERROR] " + message;
    }

    public String getMessage() {
        return message;
    }
}
