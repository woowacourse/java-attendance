package attendance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime parseDateTime(final String input) {
        return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(final String input, final DateTimeFormatter formatter) {
        return LocalDate.parse(input, formatter);
    }
}
