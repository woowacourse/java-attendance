package domain;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate date) {
        if (checkWeekend(date)) {
            return true;
        }
        return Arrays.stream(values())
            .anyMatch(holiday -> holiday.month == date.getMonthValue()
                && holiday.day == date.getDayOfMonth());
    }

    private static boolean checkWeekend(LocalDate date) {
        return date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY;
    }
}