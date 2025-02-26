package attendance.controller.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {
    public static LocalTime convertToTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간을 올바른 형식으로 입력해주십시오.");
        }
    }
}
