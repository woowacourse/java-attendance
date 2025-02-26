package util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateTimeManager {
    public static boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY
                || Constants.HOLIDAY.contains(date.getDayOfMonth())) {
            return true;
        }
        return false;
    }
}
