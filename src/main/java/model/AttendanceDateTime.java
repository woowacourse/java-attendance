package model;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDateTime {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");

    private static LocalDateTime dateTime;

    private AttendanceDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static AttendanceDateTime of(final String dateTimeInput) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, formatter);
            return new AttendanceDateTime(dateTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException();
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
