package domain;

public enum AttendanceAlertLevel {
    DISMISSED(6, "제적"),
    COUNSEL_REQUIRED(3, "면담"),
    CAUTION(2, "경고"),
    NORMAL(0, "일반"),
    ;

    final int limit;
    final String name;
    public static final int LATE_TO_ABSENT_THRESHOLD = 3;

    AttendanceAlertLevel(int absenceLimit, String name) {
        this.limit = absenceLimit;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AttendanceAlertLevel calculateAttendanceAlertLevel(int absent, int late) {
        int absentTotal = absent + (late / LATE_TO_ABSENT_THRESHOLD);
        if (absentTotal >= DISMISSED.limit) {
            return DISMISSED;
        }
        if (absentTotal >= COUNSEL_REQUIRED.limit) {
            return COUNSEL_REQUIRED;
        }
        if (absentTotal >= CAUTION.limit) {
            return CAUTION;
        }
        return NORMAL;
    }
}
