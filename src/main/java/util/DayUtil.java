package util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayUtil {

    public static boolean isOffDay(LocalDate targetDay) {
        boolean isWeekend = targetDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
            targetDay.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHoliday = false; // TODO: 적용 필요

        return isWeekend || isHoliday;
    }

    public static LocalDate now() {
        return LocalDate.now();
    }
}
