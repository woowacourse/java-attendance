package attendance.controller.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {
    public static LocalDateTime convertToDateTime(String inputDay, String inputTime, LocalDate date) {
        validateDay(inputDay);
        int day =  Integer.parseInt(inputDay);
        return LocalDateTime.of(date.withDayOfMonth(day), LocalTime.parse(inputTime));
    }

    public static LocalTime convertToTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간을 올바른 형식으로 입력해주십시오.");
        }
    }

    private static void validateDay(String inputDay) {
        try {
            int day =  Integer.parseInt(inputDay);
            if (day < 1 || day > 12) {
                throw new IllegalArgumentException("[ERROR] 올바른 날짜를 입력해주세요.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 날짜를 입력해주세요.");
        }
    }
}
