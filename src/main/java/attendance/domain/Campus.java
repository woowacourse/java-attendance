package attendance.domain;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Campus {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private static final LocalTime REGULAR_START_TIME = LocalTime.of(10, 0, 0);
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0, 0);
    private static final int PRESENT_LIMIT_MINUTES = 5;
    private static final int LATE_LIMIT_MINUTES = 30;

    public static boolean isOffDay(final LocalDateTime attendance) {
        if (isWeekend(attendance)) {
            return true;
        }
        return isHoliday(attendance);
    }

    private static boolean isWeekend(final LocalDateTime attendance) {
        final DayOfWeek dayOfWeek = attendance.getDayOfWeek();
        return dayOfWeek == SATURDAY || dayOfWeek == SUNDAY;
    }

    private static boolean isHoliday(final LocalDateTime attendance) {
        return CHRISTMAS.equals(LocalDate.from(attendance));
    }

    public static AttendanceStatus calculateAttendanceStatus(final LocalDateTime attendance) {
        final LocalTime attendanceTime = LocalTime.from(attendance);
        if (attendance.getDayOfWeek() == MONDAY) {
            return calculateAttendanceStatus(attendanceTime, MONDAY_START_TIME);
        }
        return calculateAttendanceStatus(attendanceTime, REGULAR_START_TIME);
    }

    private static AttendanceStatus calculateAttendanceStatus(final LocalTime attendance, final LocalTime startTime) {
        if (attendance.isAfter(startTime.plusMinutes(LATE_LIMIT_MINUTES))) {
            return AttendanceStatus.ABSENT;
        }
        if (attendance.isAfter(startTime.plusMinutes(PRESENT_LIMIT_MINUTES))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.PRESENT;
    }
}
