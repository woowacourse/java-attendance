package attendance.domain;

import static attendance.SystemDateConfig.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import attendance.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";

    public static Attendance from(LocalDateTime dateTime) {
        AttendanceStatus attendanceStatus = decideAttendanceStatus(dateTime);
        return new Attendance(dateTime, attendanceStatus);
    }

    private static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        validate(dateTime);
        Schedule schedule = Schedule.getScheduleOnDay(dateTime);
        return AttendanceStatus.decideStatus(dateTime.toLocalTime(), schedule.getTime());
    }

    private static void validate(LocalDateTime dateTime) {
        var date = dateTime.toLocalDate();
        validateIsWeekend(date);
        validateIsHoliday(date);
        Schedule.validateCampusSchedule(dateTime);
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

}
