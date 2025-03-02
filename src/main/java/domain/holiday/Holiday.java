package domain.holiday;

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

    public static boolean isWeekendOrHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                isHoliday(date);
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> (
                        holiday.month == date.getMonthValue() &&
                                holiday.day == date.getDayOfMonth()
                ));
    }
}
