package attendance.domain;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석")
    ;

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
