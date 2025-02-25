package attendance.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeParser {

    private static final String PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private static final String INPUT_DATETIME_MESSAGE = "[ERROR] 잘못된 날짜와 시간 형식입니다.";
    private static final String INPUT_DATE_MESSAGE = "[ERROR] 잘못된 날짜 형식입니다.";
    private static final String INPUT_TIME_MESSAGE = "[ERROR] 잘못된 시간 형식입니다.";

    private DateTimeParser() {
    }

    public static LocalDateTime parseDateTime(final String input) {
        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INPUT_DATETIME_MESSAGE);
        }
    }

    public static LocalDate parseDateByDay(final LocalDate date, final int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INPUT_DATE_MESSAGE);
        }
    }

    public static LocalTime parseTime(final String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INPUT_TIME_MESSAGE);
        }
    }
}
