package domain;

public enum AttendanceStatus {
    PRESENT("출석"),
    LATE("지각"),
    ABSENT("결석"),
    NO_SHOW("결석");

    public String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
