package attendance.domain;

public enum AttendanceDismissStatus {

    NEED_MEETING("면담"),
    WARNING("경고"),
    DISMISS("제적"),
    NONE(""),
    ;

    private static final int DISMISS_STANDARD = 5;
    private static final int MEETING_STANDARD = 3;
    private static final int WARNING_STANDARD = 2;
    private static final int LATE_WEIGHT = 3;

    private final String status;

    AttendanceDismissStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceDismissStatus getAttendanceDismissStatus(int absenceCount) {
        if (absenceCount > DISMISS_STANDARD) {
            return AttendanceDismissStatus.DISMISS;
        }
        if (absenceCount >= MEETING_STANDARD) {
            return AttendanceDismissStatus.NEED_MEETING;
        }
        if (absenceCount == WARNING_STANDARD) {
            return AttendanceDismissStatus.WARNING;
        }
        return AttendanceDismissStatus.NONE;
    }

    public static AttendanceDismissStatus calculateAttendanceDismiss(int absence, int late) {
        var absenceCount = (late / LATE_WEIGHT) + absence;
        return getAttendanceDismissStatus(absenceCount);
    }
}
