package attendance.controller.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    private static final String TIME_FORMAT = "HH:mm";

    private TimeFormatter() {
    }

    public static LocalDateTime format(final LocalDate now, final String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalTime time = LocalTime.parse(inputDateTime, formatter);
        return LocalDateTime.of(now, time);
    }
}
