package attendance.controller.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {
    private static final String TIME_FORMAT = "HH:mm";
    private static final int START_DAY_OF_YEAR = 1;
    private static final int LAST_DAY_OF_YEAR = 31;

    private DateTimeConverter() {
    }

    public static LocalDateTime convertToDateTime(final String inputDay, final String inputTime, final LocalDate date) {
        validateDay(inputDay);
        int day = Integer.parseInt(inputDay);
        return LocalDateTime.of(date.withDayOfMonth(day), LocalTime.parse(inputTime));
    }

    public static LocalTime convertToTime(final String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try {
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간을 올바른 형식으로 입력해주십시오.");
        }
    }

    private static void validateDay(final String inputDay) {
        try {
            int day = Integer.parseInt(inputDay);
            if (day < START_DAY_OF_YEAR || day > LAST_DAY_OF_YEAR) {
                throw new IllegalArgumentException("[ERROR] 올바른 날짜를 입력해주세요.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 날짜를 입력해주세요.");
        }
    }
}
