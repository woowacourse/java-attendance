package domain.policy;

public enum AttendanceStateRule {

    ATTEND(0, "출석"),
    LATE(5, "지각"),
    ABSENT(30, "결석"),
    ;

    private final int cutoffMinute;
    private final String description;

    AttendanceStateRule(int cutoffMinute, String description) {
        this.cutoffMinute = cutoffMinute;
        this.description = description;
    }

    public static AttendanceStateRule decisionState(long lateMinutes) {
        if (lateMinutes > ABSENT.cutoffMinute) {
            return ABSENT;
        }
        if (lateMinutes > LATE.cutoffMinute) {
            return LATE;
        }
        return ATTEND;
    }

    public String getDescription() {
        return description;
    }
}
