package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Evaluator {

    public static boolean isOpenDate(LocalDate date) {
        if (isWeekend(date.getDayOfWeek()) || isHoliday(date)) {
            return false;
        }
        return true;
    }

    public static boolean isOpenTime(LocalTime time) {
        return time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(23, 0));
    }

    private static boolean isWeekend(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    private static boolean isHoliday(LocalDate date) {
        if (date.equals(LocalDate.of(2024, 12, 25))) {
            return true;
        }
        return false;
    }
}
