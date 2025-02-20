package model;

public enum AttendanceStatus {

    ABSENT("결석"),
    ATTENDANCE("출석"),
    LATE("지각");

    private final String state;

    AttendanceStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
