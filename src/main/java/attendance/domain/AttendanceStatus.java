package attendance.domain;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
