package domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public static AttendanceStatus findByAttendanceTime(Week day, LocalTime attendanceTime) {
        if (isAbsence(attendanceTime)) {
            return ABSENCE;
        }
        if (isAttendance(day, attendanceTime)) {
            return ATTENDANCE;
        }
        if (isTardiness(day, attendanceTime)) {
            return TARDINESS;
        }
        return ABSENCE;
    }

    private static boolean isAbsence(final LocalTime attendanceTime) {
        return attendanceTime.isBefore(START_TIME) || attendanceTime.isAfter(END_TIME);
    }

    private static boolean isAttendance(final Week day, final LocalTime attendanceTime) {
        return isWithinTimeRange(attendanceTime, day.getAttendanceTime(), 5);
    }

    private static boolean isTardiness(final Week day, final LocalTime attendanceTime) {
        final LocalTime attendanceDeadline = day.getAttendanceTime().plusMinutes(5);
        return attendanceTime.isAfter(attendanceDeadline)
                && isWithinTimeRange(attendanceTime, day.getAttendanceTime(), 30);
    }

    private static boolean isWithinTimeRange(final LocalTime attendanceTime, final LocalTime baseTime,
                                             int rangeMinutes) {
        final LocalTime adjustedTime = baseTime.plusMinutes(rangeMinutes);
        return !attendanceTime.isAfter(adjustedTime);
    }

    public String getDisplayName() {
        return displayName;
    }
}
