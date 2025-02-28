package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeParser() {
    }

    public static LocalDateTime parseToLocalDateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }
}
