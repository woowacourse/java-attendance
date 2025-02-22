package attendance.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {

    private DateUtil() {}

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
            || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
