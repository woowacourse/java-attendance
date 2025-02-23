package attendance.util;

import attendance.controller.Parser;
import attendance.model.CustomClock;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {
    private DateUtil() {
    }

    public static LocalDate parseDate(String day) {
        return LocalDate.of(2024, 12, Parser.parseInt(day));
    }

    public static boolean isWeekendOrHoliday(LocalDate date, CustomClock clock) {
        return isWeekend(date) || clock.isHoliday(date);
    }


    public static boolean isWeekend(LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
