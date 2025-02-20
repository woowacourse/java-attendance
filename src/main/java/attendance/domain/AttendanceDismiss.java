package attendance.domain;

public class AttendanceDismiss {

    public static AttendanceDismissStatus calculateAttendanceDismiss(int absence, int late) {
        var absenceCount = (late / 3) + absence;
        return getAttendanceDismissStatus(absenceCount);
    }

    private static AttendanceDismissStatus getAttendanceDismissStatus(int absenceCount) {
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
}
