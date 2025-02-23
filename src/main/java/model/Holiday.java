package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {

    CHRISTMAS(12, 25),
    ;

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHolidayOrWeekend(LocalDate date) {
        return isHoliday(date) || isWeekend(date);
    }

    private static boolean isHoliday(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.month == month && holiday.day == day);
    }

    private static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
