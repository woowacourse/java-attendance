package domain.constant;

import domain.Holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class StandardDate {

    public static final LocalDate FIXED_DATE = LocalDate.of(2025, 2, 28);
    public static final LocalDate TODAY = determineToday();

    private StandardDate() {

    }

    private static LocalDate determineToday() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfWeek() == DayOfWeek.SATURDAY) return today.minusDays(1);
        if (today.getDayOfWeek() == DayOfWeek.SUNDAY) return today.plusDays(1);
        if (Holiday.check(today)) return FIXED_DATE;
        return today;
    }
}
