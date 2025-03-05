package util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InputParser {

    public static LocalTime parseTime(final String input) {
        try {
            return LocalTime.parse(input, AttendanceDateTimeFormatter.timeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 시간 입력 형식입니다.");
        }
    }

    public static LocalDate parseDayOfMonth(final String input) {
        try {
            int day = Integer.parseInt(input);
            return LocalDate.of(2024, 12, day);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 날짜(일) 입력 형식입니다.");
        }
    }

    public static LocalDate parseCsvDate(final String input) {
        try {
            return LocalDate.parse(input, AttendanceDateTimeFormatter.csvDateFormatter);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 날짜(일) 입력 형식입니다.");
        }
    }
}
