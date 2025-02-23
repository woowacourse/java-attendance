package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
    private static final LocalTime DEFAULT_SCHEDULE = LocalTime.of(10, 0);
    private static final LocalTime MONDAY_SCHEDULE = LocalTime.of(13, 0);

    private static final int ABSENCE = 30;
    private static final int LATE = 5;

    public Attendance(LocalDateTime dateTime) {
        this(dateTime, decideAttendanceStatus(dateTime));
    }

    private static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        LocalTime baseSchedule = getScheduleForDay(dateTime);
        return decideOnDefaultDay(dateTime.toLocalTime(), baseSchedule);
    }

    private static LocalTime getScheduleForDay(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return MONDAY_SCHEDULE;
        }
        return DEFAULT_SCHEDULE;
    }

    private static AttendanceStatus decideOnDefaultDay(LocalTime time, LocalTime baseSchedule) {
        if (isAfter(time, ABSENCE, baseSchedule)) {
            return AttendanceStatus.ABSENCE;
        }
        if (isAfter(time, LATE, baseSchedule)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private static boolean isAfter(LocalTime time, int minutesToAdd, LocalTime baseSchedule) {
        return time.isAfter(baseSchedule.plusMinutes(minutesToAdd));
    }
}
