package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.common.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
    private static final LocalTime DEFAULT_SCHEDULE = LocalTime.of(10, 0);
    private static final LocalTime MONDAY_SCHEDULE = LocalTime.of(13, 0);

    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    private static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";

    private static final int ABSENCE = 30;
    private static final int LATE = 5;
    private static final int WEEKENDER = 6;

    public Attendance(LocalDateTime dateTime) {
        this(dateTime, decideAttendanceStatus(dateTime));
    }

    private static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        validate(dateTime);
        LocalTime baseSchedule = getScheduleForDay(dateTime);
        return decideOnDefaultDay(dateTime.toLocalTime(), baseSchedule);
    }

    private static void validate(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek().getValue() >= WEEKENDER) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_WEEKEND_FORMAT, dateTime);
        }
        if (dateTime.toLocalTime().isBefore(LocalTime.of(8, 0))
            || dateTime.toLocalTime().isAfter(LocalTime.of(23, 0))) {
            throw new AttendanceArgumentException(OUT_OF_SCHOOL_SCHEDULE);
        }
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
