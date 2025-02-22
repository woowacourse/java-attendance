package attendance.error;

public enum ErrorMessage {

    NOT_OPERATING_TIME("캠퍼스 운영 시간이 아닙니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = "[ERROR]" + message;
    }

    public String getMessage() {
        return message;
    }
}
