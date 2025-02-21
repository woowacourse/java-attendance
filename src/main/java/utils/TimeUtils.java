package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class TimeUtils {
    public static LocalDate getDateFromDateAndTime(Map<LocalDate, LocalTime> dateAndTime) {
        return dateAndTime.entrySet().stream()
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static LocalTime getTimeFromDateAndTime(Map<LocalDate, LocalTime> dateAndTime) {
        return dateAndTime.entrySet().stream()
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}