package utils;

import java.time.MonthDay;
import java.util.Arrays;

public enum SolarCalendarHoliday {

    CHRISTMAS(MonthDay.of(12, 25));

    private final MonthDay monthDay;

    SolarCalendarHoliday(final MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public static boolean isHoliday(final MonthDay monthDay) {
        return Arrays.stream(values())
                .map(holiday -> holiday.monthDay)
                .anyMatch(day -> day.equals(monthDay));
    }
}
