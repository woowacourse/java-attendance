package attendance.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringParser {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SPLITTER = ",";

    public static LocalTime parseLocalTime(final String input) {
        try {
            return LocalTime.parse(input, TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("HH:mm 형식이 아닙니다.");
        }
    }

    public static Map<String, List<LocalDateTime>> parseFile(final List<String> lines) {
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        for (String line : lines) {
            addResult(line, result);
        }
        return result;
    }

    public static int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("숫자 형식의 문자열이 아닙니다.");
        }
    }

    private static void addResult(final String line, final Map<String, List<LocalDateTime>> result) {
        String[] split = line.split(SPLITTER);
        String nickname = split[0];
        LocalDateTime attendanceTime = parseLocalDateTime(split[1]);

        createIfNotExists(result, nickname);
        List<LocalDateTime> times = result.get(nickname);
        times.add(attendanceTime);
        result.put(nickname, times);
    }

    private static void createIfNotExists(final Map<String, List<LocalDateTime>> result, final String nickname) {
        if (!result.containsKey(nickname)) {
            result.put(nickname, new ArrayList<>());
        }
    }

    private static LocalDateTime parseLocalDateTime(final String input) {
        try {
            return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("yyyy-MM-dd HH:mm 형식에 맞춰 작성해주세요.");
        }
    }
}
