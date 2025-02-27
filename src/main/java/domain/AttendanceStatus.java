package domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENT,
    LATE,
    ABSENT;

    private static final int LATE_CUTOFF = 30;
    private static final int ATTENDANCE_CUTOFF = 5;

    public static AttendanceStatus findByAttendanceDateTime(final AttendanceDateTime attendanceDateTime,
                                                            final AttendanceTimePolicy attendanceTimePolicy) {
        final LocalTime attendanceTime = attendanceTimePolicy.getAttendanceTime();
        final LocalTime entranceTime = attendanceDateTime.getDateTime()
                .toLocalTime();

        if (isNotWithinOperatingHours(entranceTime)) {
            return ABSENT;
        }
        if (isPresent(entranceTime, attendanceTime)) {
            return PRESENT;
        }
        if (isLate(entranceTime, attendanceTime)) {
            return LATE;
        }
        return ABSENT;
    }

    private static boolean isNotWithinOperatingHours(final LocalTime entranceTime) {
        return entranceTime.isBefore(AttendanceTimePolicy.START_TIME) || entranceTime.isAfter(
                AttendanceTimePolicy.END_TIME);
    }

    private static boolean isPresent(final LocalTime entranceTime, final LocalTime attendanceTime) {
        return !entranceTime.isAfter(getCutoffTime(attendanceTime, ATTENDANCE_CUTOFF));
    }

    private static boolean isLate(final LocalTime entranceTime, final LocalTime attendanceTime) {
        return !entranceTime.isAfter(getCutoffTime(attendanceTime, LATE_CUTOFF));
    }

    private static LocalTime getCutoffTime(final LocalTime attendanceTime, final int maxMinutes) {
        return attendanceTime.plusMinutes(maxMinutes);
    }
}
