package util.status;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    DANGER("경고"),
    INTERVIEW("면담"),
    EXPELLED("제적");

    private final String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}