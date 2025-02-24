package domain;

import domain.constants.AttendanceStatus;
import domain.constants.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Attendance {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime dateTime;
    private final boolean isEmpty;

    public Attendance(final LocalDateTime dateTime, final boolean isEmpty) {
        this.dateTime = dateTime;
        this.isEmpty = isEmpty;
    }

    public static Attendance of(final String dateTime) {
        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, FORMATTER);
            return new Attendance(parsedDateTime, false);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE_FORMAT.getMessage());
        }

    }

    public static Attendance of(final LocalDateTime dateTime) {
        return new Attendance(dateTime, false);
    }

    public static Attendance empty(final LocalDate date) {
        final LocalTime emptyTime = LocalTime.MAX;
        return new Attendance(LocalDateTime.of(date, emptyTime), true);
    }

    public AttendanceStatus calculateStatus() {
        final LocalTime time = dateTime.toLocalTime();
        if (dateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceStatus.of(time, 13, 0);
        }
        return AttendanceStatus.of(time, 10, 0);

    }

    public boolean matchDate(final LocalDate localDate) {
        return dateTime.toLocalDate().equals(localDate);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Attendance) {
            final Attendance target = (Attendance) o;
            return dateTime.toLocalDate().equals(target.dateTime.toLocalDate());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dateTime.toLocalDate());
    }
}
