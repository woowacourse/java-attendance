package attendance.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringParser {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalTime parseLocalTime(final String input) {
        try {
            return LocalTime.parse(input, TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("문자열 형식에 맞지 않습니다.");
        }
    }

    public static Map<String, LocalDateTime> parseFile(final List<String> lines) {
        Map<String, LocalDateTime> result = new HashMap<>();
        for (String line : lines) {
            String[] split = line.split(",");
            result.put(split[0], parseLocalDateTime(split[1]));
        }
        return result;
    }

    private static LocalDateTime parseLocalDateTime(final String input) {
        try {
            return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("문자열 형식에 맞지 않습니다.");
        }
    }
}
