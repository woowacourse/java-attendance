package domain.rule;

public enum AttendanceStateRule {
    ATTEND("출석", 0),
    LATE("지각", 5),
    ABSENT("결석", 30),
    ;

    public final String description;
    public final int limit;

    AttendanceStateRule(String description, int limit) {
        this.description = description;
        this.limit = limit;
    }
}
