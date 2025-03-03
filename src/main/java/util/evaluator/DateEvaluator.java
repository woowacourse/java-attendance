package util.evaluator;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateEvaluator {

    private DateEvaluator() {
    }

    public static boolean isOpenDate(LocalDate date) {
        return !isWeekend(date.getDayOfWeek()) && !isHoliday(date);
    }

    private static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private static boolean isHoliday(LocalDate date) {
        return date.equals(LocalDate.of(2024, 12, 25));
    }
}
