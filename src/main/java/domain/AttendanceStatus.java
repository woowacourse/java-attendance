package domain;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    public final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
