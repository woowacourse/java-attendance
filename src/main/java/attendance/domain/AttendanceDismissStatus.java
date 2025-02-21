package attendance.domain;

public enum AttendanceDismissStatus {
    NEED_MEETING("면담"),
    WARNING("경고"),
    DISMISS("제적"),
    NONE(""),
    ;

    public String getStatus() {
        return status;
    }

    private final String status;

    AttendanceDismissStatus(String status) {
        this.status = status;
    }

    public static AttendanceDismissStatus getAttendanceDismissStatus(int absenceCount) {
        if (absenceCount > 5) {
            return AttendanceDismissStatus.DISMISS;
        }
        if (absenceCount >= 3) {
            return AttendanceDismissStatus.NEED_MEETING;
        }
        if (absenceCount == 2) {
            return AttendanceDismissStatus.WARNING;
        }
        return AttendanceDismissStatus.NONE;
    }

    public static AttendanceDismissStatus calculateAttendanceDismiss(int absence, int late) {
        var absenceCount = (late / 3) + absence;
        return getAttendanceDismissStatus(absenceCount);
    }
}
