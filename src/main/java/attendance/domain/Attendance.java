package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import attendance.common.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    private static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";

    private static final List<Integer> datOfHoliday = List.of(25);

    public Attendance(LocalDateTime dateTime) {
        this(dateTime, decideAttendanceStatus(dateTime));
    }

    private static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        validate(dateTime);
        LocalTime baseSchedule = getScheduleForDay(dateTime);
        return decideOnDefaultDay(dateTime.toLocalTime(), baseSchedule);
    }

    private static void validate(LocalDateTime dateTime) {
        validateIsWeekend(dateTime);
        validateIsHoliday(dateTime);
        validateIsDuringCampusSchedule(dateTime);
    }

    private static void validateIsHoliday(LocalDateTime dateTime) {
        for (int i : datOfHoliday) {
            if (dateTime.getDayOfMonth() == i) {
                throw new AttendanceArgumentException(CANNOT_ATTENDANCE_WEEKEND_FORMAT, dateTime);
            }
        }
    }

    private static void validateIsWeekend(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue()) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_WEEKEND_FORMAT, dateTime);
        }
    }

    private static void validateIsDuringCampusSchedule(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        if (isDuringCampusSchedule(time)) {
            throw new AttendanceArgumentException(OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    private static boolean isDuringCampusSchedule(LocalTime time) {
        return time.isBefore(Schedule.CAMPUS_OPEN) || time.isAfter(Schedule.CAMPUS_CLOSE);
    }

    private static LocalTime getScheduleForDay(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return Schedule.MONDAY;
        }
        return Schedule.DEFAULT;
    }

    private static AttendanceStatus decideOnDefaultDay(LocalTime time, LocalTime baseSchedule) {
        if (isAfter(time, Penalty.ABSENCE, baseSchedule)) {
            return AttendanceStatus.ABSENCE;
        }
        if (isAfter(time, Penalty.LATE, baseSchedule)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private static boolean isAfter(LocalTime time, int minutesToAdd, LocalTime baseSchedule) {
        return time.isAfter(baseSchedule.plusMinutes(minutesToAdd));
    }

    private static class Schedule {
        private static final LocalTime DEFAULT = java.time.LocalTime.of(10, 0);
        private static final LocalTime MONDAY = LocalTime.of(13, 0);
        private static final LocalTime CAMPUS_OPEN = LocalTime.of(8, 0);
        private static final LocalTime CAMPUS_CLOSE = LocalTime.of(23, 0);
    }

    private static class Penalty {
        private static final int ABSENCE = 30;
        private static final int LATE = 5;
    }

}
