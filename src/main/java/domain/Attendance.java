package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Attendance that = (Attendance) o;
        return Objects.equals(attendanceDate(), that.attendanceDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDate());
    }
}
