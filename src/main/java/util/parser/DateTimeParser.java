package util.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeParser() {
    }

    public static LocalDateTime parseStringToDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }

}
