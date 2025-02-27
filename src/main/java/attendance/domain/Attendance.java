package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";

    public static Attendance of(LocalDateTime dateTime, SystemDateTime systemDateTime) {
        AttendanceStatus attendanceStatus = decideAttendanceStatus(dateTime, systemDateTime);
        return new Attendance(dateTime, attendanceStatus);
    }

    private static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime, SystemDateTime systemDateTime) {
        validate(dateTime, systemDateTime);
        Schedule schedule = Schedule.getScheduleOnDay(dateTime);
        return AttendanceStatus.decideStatus(dateTime.toLocalTime(), schedule.getTime());
    }

    private static void validate(LocalDateTime dateTime, SystemDateTime systemDateTime) {
        var date = dateTime.toLocalDate();
        if (!systemDateTime.isWorkingDay(date)) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_WEEKEND_FORMAT, date);
        }
        Schedule.validateCampusSchedule(dateTime);
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
