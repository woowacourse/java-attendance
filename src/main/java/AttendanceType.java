public enum AttendanceType {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String name;

    AttendanceType(String name) {
        this.name = name;
    }
}
