package util;

import domain.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        return Holiday.isHoliday(date.getMonthValue(), date.getDayOfMonth());
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static boolean isInRange(LocalTime startTime, LocalTime endTime, LocalTime targetTime) {
        return (targetTime.equals(startTime) || targetTime.isAfter(startTime))
                && (targetTime.equals(endTime) || targetTime.isBefore(endTime));
    }
}
