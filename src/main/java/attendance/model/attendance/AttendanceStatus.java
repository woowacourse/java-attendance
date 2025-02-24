package attendance.model.attendance;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    ABSENCE("결석"),
    LATE("지각");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
