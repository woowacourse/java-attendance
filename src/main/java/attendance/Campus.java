package attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Campus {
    private static final LocalTime REGULAR_START_TIME = LocalTime.of(10, 0, 0);
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0, 0);
    private static final int PRESENT_LIMIT_MINUTES = 5;
    private static final long LATE_LIMIT_MINUTES = 30;

    public static AttendanceStatus calculateAttendanceStatus(final LocalDateTime attendance) {
        if (attendance.getDayOfWeek() == DayOfWeek.MONDAY) {
            if (attendance.toLocalTime().isAfter(MONDAY_START_TIME.plusMinutes(LATE_LIMIT_MINUTES))) {
                return AttendanceStatus.ABSENT;
            }
            if (attendance.toLocalTime().isAfter(MONDAY_START_TIME.plusMinutes(PRESENT_LIMIT_MINUTES))) {
                return AttendanceStatus.LATE;
            }
            return AttendanceStatus.PRESENT;
        }
        if (attendance.toLocalTime().isAfter(REGULAR_START_TIME.plusMinutes(LATE_LIMIT_MINUTES))) {
            return AttendanceStatus.ABSENT;
        }
        if (attendance.toLocalTime().isAfter(REGULAR_START_TIME.plusMinutes(PRESENT_LIMIT_MINUTES))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.PRESENT;
    }
}
