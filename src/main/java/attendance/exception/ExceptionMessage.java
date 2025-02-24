package attendance.exception;

public enum ExceptionMessage {
    ALREADY_ATTENDANCE("[ERROR] 이미 출석을 완료했습니다! 수정 기능을 활용해주세요!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
