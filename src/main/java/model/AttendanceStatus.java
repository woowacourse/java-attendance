package model;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private static final int ABSENCE_OVER_LIMIT = 30;
    private static final int TARDINESS_OVER_LIMIT = 30;

    private final String displayName;

    AttendanceStatus(final String displayName) {
        this.displayName = displayName;
    }

    public static AttendanceStatus findByAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        final BusinessHours businessHours = BusinessHours.find(attendanceDateTime);
        final AttendanceTime startTime = businessHours.getStartTime();

        if (startTime.isBeforeToPlus(attendanceDateTime, ABSENCE_OVER_LIMIT)) {
            return ABSENCE;
        }

        if (startTime.isBeforeToPlus(attendanceDateTime, TARDINESS_OVER_LIMIT)) {
            return TARDINESS;
        }
        return ATTENDANCE;
    }
}
