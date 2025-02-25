package util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateTimeUtil {
    public static boolean isOffDay(LocalDate date) {
        return isWeekend(date) || isHoliday(date);
    }

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        return false;
    }
}
