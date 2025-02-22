package attendance.model;

public enum AttendanceWarning {
    OUT(5),
    NEED_MEETING(3),
    WARNING(2),
    NONE(0),
    ;

    public static final int LATES_PER_ABSENCE = 3;
    private final int absenceCount;

    AttendanceWarning(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static AttendanceWarning from(long absenceCount) {
        if (absenceCount > OUT.absenceCount) {
            return OUT;
        }
        if (absenceCount >= NEED_MEETING.absenceCount) {
            return NEED_MEETING;
        }
        if (absenceCount >= WARNING.absenceCount) {
            return WARNING;
        }
        return NONE;
    }
    
}
