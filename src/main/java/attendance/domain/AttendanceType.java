package attendance.domain;

public enum AttendanceType {
    SAFE("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String type;

    AttendanceType(String type) {
        this.type = type;
    }
}
