package domain.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
    public static LocalDateTime assembleDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
}
