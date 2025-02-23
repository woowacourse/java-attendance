package attendance.domain;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    LATE_ABSENCE("결석"),
    ABSENCE("결석");

    private final String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
