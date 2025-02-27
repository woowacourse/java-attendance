package attendance.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringParser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수 문자열이여야합니다.");
        }
    }

    public static LocalTime parseLocalTime(final String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간이 HH:MM 형식에 맞지 않습니다.");
        }
    }

    public static LocalDate parseLocalDate(final int year, final int month, final String inputDay) {
        int day = parseInt(inputDay);
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 유효한 일자이여야합니다.");
        }
    }

    public static LocalDateTime parseLocalDateTime(final String input) {
        try {
            return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 및 시간이 yyyy-MM-dd HH:mm 형식에 맞지 않습니다.");
        }
    }
}
