package attendance.controller.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeFormatter {
    private static final String TIME_FORMAT = "HH:mm";

    private TimeFormatter() {
    }

    public static LocalDateTime format(final LocalDate now, final String inputDateTime) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
            LocalTime time = LocalTime.parse(inputDateTime, formatter);
            return LocalDateTime.of(now, time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("[ERROR] 시간은 %s 형식으로 입력해주세요.", TIME_FORMAT));
        }
    }
}
