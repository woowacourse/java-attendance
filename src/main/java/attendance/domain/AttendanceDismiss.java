package attendance.domain;

public class AttendanceDismiss {

    public static SanctionLevel calculateAttendanceDismiss(int absence, int late) {
        var absenceCount = (late / 3) + absence;
        return getAttendanceDismissStatus(absenceCount);
    }

    private static SanctionLevel getAttendanceDismissStatus(int absenceCount) {
        if (absenceCount > 5) {
            return SanctionLevel.DISMISS;
        }
        if (absenceCount >= 3) {
            return SanctionLevel.NEED_MEETING;
        }
        if (absenceCount == 2) {
            return SanctionLevel.WARNING;
        }
        return SanctionLevel.NONE;
    }
}
