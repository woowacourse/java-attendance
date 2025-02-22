package attendance.utility;

import attendance.exception.ExceptionMessage;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeParser() {
    }

    public static LocalDateTime parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            String message = ExceptionMessage.INVALID_DATE_TIME_FORMAT.getContent();
            throw new IllegalArgumentException(message);
        }
    }

    public static LocalDate parseDateByDay(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            String message = ExceptionMessage.INVALID_DATE_FORMAT.getContent();
            throw new IllegalArgumentException(message);
        }
    }

    public static LocalTime parseTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            String message = ExceptionMessage.INVALID_TIME_FORMAT.getContent();
            throw new IllegalArgumentException(message);
        }
    }
}
