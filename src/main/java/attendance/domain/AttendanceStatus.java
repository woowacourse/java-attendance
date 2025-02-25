package attendance.domain;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String value;

    AttendanceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
