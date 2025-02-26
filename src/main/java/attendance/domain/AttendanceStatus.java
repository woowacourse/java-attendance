package attendance.domain;

public enum AttendanceStatus {
    LATE("지각"),
    ABSENCE("결석"),
    ATTEND("출석"),
    ;

    private final String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
