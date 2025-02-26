package attendance.domain.attendance;

import static attendance.SystemDateConfig.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    private static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";

    public static Attendance from(LocalDateTime dateTime) {
        AttendanceStatus attendanceStatus = decideAttendanceStatus(dateTime);
        return new Attendance(dateTime, attendanceStatus);
    }

    private static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        validate(dateTime);
        LocalTime baseSchedule = getScheduleForDay(dateTime);
        return decideOnDefaultDay(dateTime.toLocalTime(), baseSchedule);
    }

    private static void validate(LocalDateTime dateTime) {
        var date = dateTime.toLocalDate();
        validateIsWeekend(date);
        validateIsHoliday(date);
        validateIsDuringCampusSchedule(dateTime);
    }

    private static void validateIsHoliday(LocalDate date) {
        boolean isHoliday = DAT_OF_HOLIDAY.stream()
            .anyMatch(day -> date.getDayOfMonth() == day);

        if (isHoliday) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_WEEKEND_FORMAT, date);
        }
    }

    private static void validateIsWeekend(LocalDate date) {
        if (date.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue()) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_WEEKEND_FORMAT, date);
        }
    }

    private static void validateIsDuringCampusSchedule(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        if (time.isBefore(Schedule.CAMPUS_OPEN) || time.isAfter(Schedule.CAMPUS_CLOSE)) {
            throw new AttendanceArgumentException(OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    private static LocalTime getScheduleForDay(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return Schedule.MONDAY;
        }
        return Schedule.DEFAULT;
    }

    private static AttendanceStatus decideOnDefaultDay(LocalTime time, LocalTime baseSchedule) {
        if (isAfter(time, AttendanceStatus.ABSENCE.getMinutes(), baseSchedule)) {
            return AttendanceStatus.ABSENCE;
        }
        if (isAfter(time, AttendanceStatus.LATE.getMinutes(), baseSchedule)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private static boolean isAfter(LocalTime time, int minutesToAdd, LocalTime baseSchedule) {
        return time.isAfter(baseSchedule.plusMinutes(minutesToAdd));
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    private static class Schedule {
        private static final LocalTime DEFAULT = java.time.LocalTime.of(10, 0);
        private static final LocalTime MONDAY = LocalTime.of(13, 0);
        private static final LocalTime CAMPUS_OPEN = LocalTime.of(8, 0);
        private static final LocalTime CAMPUS_CLOSE = LocalTime.of(23, 0);

        private Schedule() {
        }
    }
}
