package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(
        AttendanceDate attendanceDate,
        AttendanceTime attendanceTime,
        AttendanceStatus status
) {
    public static Attendance of(final LocalDateTime localDateTime) {
        final LocalDate date = localDateTime.toLocalDate();
        final LocalTime time = localDateTime.toLocalTime();
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        final AttendanceStatus status = AttendanceStatus.findByTime(dayOfWeek, time);
        final AttendanceDate attendanceDate = new AttendanceDate(date);
        final AttendanceTime attendanceTime = AttendanceTime.of(dayOfWeek, time);
        return new Attendance(attendanceDate, attendanceTime, status);
    }
}
