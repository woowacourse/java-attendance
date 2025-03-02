package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Evaluator {

    private Evaluator() {
    }

    public static boolean isOpenDate(LocalDate date) {
        return !isWeekend(date.getDayOfWeek()) && !isHoliday(date);
    }

    public static boolean isOpenTime(LocalTime time) {
        return time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(23, 0));
    }

    private static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private static boolean isHoliday(LocalDate date) {
        return date.equals(LocalDate.of(2024, 12, 25));
    }
}
