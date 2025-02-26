package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    public static LocalDateTime StringToLocalDateTime (String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }
}
