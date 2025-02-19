package attendance.domain;

public class AttendanceDismiss {

    public static AttendanceDismissStatus calculateAttendanceDismiss(int late, int absence) {
        int absenceCount = (late / 3) + absence;
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
