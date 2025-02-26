package domain;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String expression;

    AttendanceStatus(String expression) {
        this.expression = expression;
    }

    public AttendanceStatus of(Attendance attendance) {

    }
}
