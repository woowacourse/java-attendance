package attendance.model;

public enum AttendanceWarning {
    OUT("제적", 5),
    NEED_MEETING("면담", 3),
    WARNING("경고", 2),
    NONE("해당없음", 0),
    ;

    public static final int LATES_PER_ABSENCE = 3;

    private final String title;
    private final int absenceCount;

    AttendanceWarning(String title, int absenceCount) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }
}
