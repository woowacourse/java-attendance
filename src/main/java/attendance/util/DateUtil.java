package attendance.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

    private DateUtil() {}

    public static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
            || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isWeekend(LocalDateTime dateTime) {
        return isWeekend(dateTime.toLocalDate());
    }
}
