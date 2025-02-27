package model;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private final String displayName;

    AttendanceStatus(final String displayName) {
        this.displayName = displayName;
    }

    public static AttendanceStatus findByAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        final BusinessHours businessHours = BusinessHours.find(attendanceDateTime);
        final AttendanceTime startTime = businessHours.getStartTime();

        if (startTime.getTime().plusMinutes(30).isBefore(attendanceDateTime.getTime())) {
            return ABSENCE;
        }

        if (startTime.getTime().plusMinutes(5).isBefore(attendanceDateTime.getTime())) {
            return TARDINESS;
        }
        return ATTENDANCE;
    }
}
