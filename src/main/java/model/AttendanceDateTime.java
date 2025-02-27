package model;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public DayOfWeek getDayOfWeek() {
        return dateTime.getDayOfWeek();
    }
}
