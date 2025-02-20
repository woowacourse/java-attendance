package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public static boolean isOffDay(LocalDate targetDay) {
        boolean isWeekend = targetDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
            targetDay.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHoliday = false; // TODO: 적용 필요

        return isWeekend || isHoliday;
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }
    public static LocalTime nowTime() {
        return LocalTime.now();
    }
}
