package util;

import java.time.LocalDate;

public class DateTimeUtil {
    public static boolean isOffDay(LocalDate date) {
        return isWeekend(date) || isHoliday(date);
    }

    public static boolean isWeekend(LocalDate date) {
        return true;
    }

    public static boolean isHoliday(LocalDate date) {
        return true;
    }
}
