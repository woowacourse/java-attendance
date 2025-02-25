package domain;

public enum AttendanceState {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석");

    private String state;

    AttendanceState(String state) {
        this.state = state;
    }
}
