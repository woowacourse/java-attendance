package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public record AttendanceRecord(
        AttendanceDate attendanceDate,
        AttendanceTime attendanceTime,
        AttendanceStatus status
) {
    public static AttendanceRecord of(final LocalDateTime localDateTime) {
        final LocalDate date = localDateTime.toLocalDate();
        final LocalTime time = localDateTime.toLocalTime();
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        final AttendanceStatus status = AttendanceStatus.findByTime(dayOfWeek, time);
        final AttendanceDate attendanceDate = new AttendanceDate(date);
        final AttendanceTime attendanceTime = AttendanceTime.of(dayOfWeek, time);
        return new AttendanceRecord(attendanceDate, attendanceTime, status);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(attendanceDate(), that.attendanceDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDate());
    }
}
