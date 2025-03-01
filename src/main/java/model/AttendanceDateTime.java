package model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceDateTime {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");

    private final LocalDateTime dateTime;

    private AttendanceDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static AttendanceDateTime of(final String dateTimeInput) {
        try {
            final LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, formatter);
            return new AttendanceDateTime(dateTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException();
        }
    }

    public static AttendanceDateTime of(final LocalDate date, final AttendanceTime attendanceTime) {
        final LocalDateTime dateTime = LocalDateTime.of(date, attendanceTime.getTime());
        return new AttendanceDateTime(dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public DayOfWeek getDayOfWeek() {
        return dateTime.getDayOfWeek();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dateTime);
    }
}
