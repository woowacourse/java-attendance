package attendance.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringParser {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalTime parseLocalTime(final String input) {
        try {
            return LocalTime.parse(input, TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("문자열 형식에 맞지 않습니다.");
        }
    }
}
