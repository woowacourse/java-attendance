package attendance.model;

public enum Attendance {
    ATTEND(0),
    LATE(5),
    ABSENCE(30),
    ;

    private final int lateMinute;

    Attendance(int lateMinute) {
        this.lateMinute = lateMinute;
    }

    public static Attendance from(long minuteDelta) {
        if (minuteDelta > ABSENCE.lateMinute) {
            return ABSENCE;
        }
        if (minuteDelta > LATE.lateMinute) {
            return LATE;
        }
        return ATTEND;
    }
}
