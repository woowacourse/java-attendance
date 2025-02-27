package domain.policy;

public enum AttendanceState {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석");

    public final String description;

    AttendanceState(String description) {
        this.description = description;
    }
}
