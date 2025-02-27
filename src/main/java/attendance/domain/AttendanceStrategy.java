package attendance.domain;

public enum AttendanceStrategy {
    MONDAY_START_HOUR(13),
    START_HOUR(10),
    ABSENCE_MINUTE(30),
    LATE_MINUTE(5),
    LATE_TO_ABSENCE_UNIT(3),
    ;

    private final int criteria;

    AttendanceStrategy(int criteria) {
        this.criteria = criteria;
    }

    public int getCriteria() {
        return criteria;
    }
}
