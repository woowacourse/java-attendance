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

    public static boolean isHoliday(LocalDate date) {
        return isWeekend(date) || isPublicHoliday(date);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isPublicHoliday(LocalDate date) {
        return Arrays.stream(values()).anyMatch(holiday -> holiday.isSameDate(date));
    }

    public boolean isSameDate(LocalDate date) {
        return date.getMonthValue() == this.month && date.getDayOfMonth() == this.day;
    }
}
