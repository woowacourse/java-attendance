package domain.policy.time.rule;

public enum AttendanceStateRule {

    ATTEND(0, "출석"),
    LATE(5, "지각"),
    ABSENT(30, "결석"),
    ;

    private final int cutoff;
    private final String description;

    AttendanceStateRule(int cutoff, String description) {
        this.cutoff = cutoff;
        this.description = description;
    }

    public static AttendanceStateRule decisionState(long lateMinutes) {
        if (lateMinutes > ABSENT.cutoff) {
            return ABSENT;
        }
        if (lateMinutes > LATE.cutoff) {
            return LATE;
        }
        return ATTEND;
    }

    public String getDescription() {
        return description;
    }
}
