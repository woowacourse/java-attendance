package domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private final String displayName;

    private static final LocalTime startTime = LocalTime.of(8, 0);
    private static final LocalTime endTime = LocalTime.of(23, 0);

    AttendanceStatus(final String displayName) {
        this.displayName = displayName;
    }

    public static AttendanceStatus findByAttendanceTime(final Week day, final LocalTime attendanceTime) {
        if (attendanceTime.isBefore(startTime) || attendanceTime.isAfter(endTime)) {
            return ABSENCE;
        }
        if (isBeforeAttendanceLimit(day, attendanceTime, 5) || equalsAttendanceLimit(day, attendanceTime, 5)) {
            return ATTENDANCE;
        }
        if (isBeforeAttendanceLimit(day, attendanceTime, 30) || equalsAttendanceLimit(day, attendanceTime, 30)) {
            return TARDINESS;
        }
        return ABSENCE;
    }

    public String getDisplayName() {
        return displayName;
    }

    private static boolean isBeforeAttendanceLimit(final Week day, final LocalTime attendanceTime, int minute) {
        return attendanceTime.isBefore(day.getAttendanceTime().plusMinutes(minute));
    }

    private static boolean equalsAttendanceLimit(final Week day, final LocalTime attendanceTime, int minute) {
        return attendanceTime.equals(day.getAttendanceTime().plusMinutes(minute));
    }
}
