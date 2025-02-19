package util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayUtil {

    public static boolean isOffDay(LocalDate today, int day) {
        LocalDate targetDay = LocalDate.of(today.getYear(), today.getMonth(), day);
        boolean isWeekend = targetDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
            targetDay.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHoliday = false; // TODO: 적용 필요

        return isWeekend || isHoliday;
    }
}
