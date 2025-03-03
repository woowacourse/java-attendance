package domain;

import java.time.DayOfWeek;
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

    public static boolean isHoliday(LocalDate day) {
        return isWeekend(day) || isPublicHoliday(day);
    }

    private static boolean isWeekend(LocalDate day) {
        return day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isPublicHoliday(LocalDate day) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.month == day.getMonthValue() && holiday.day == day.getDayOfMonth());
    }
}
