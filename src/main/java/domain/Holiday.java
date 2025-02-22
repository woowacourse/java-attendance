package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Objects;

public enum Holiday {

    CHRISTMAS("크리스마스", MonthDay.of(12, 25));

    private final String name;
    private final MonthDay monthDay;

    Holiday(final String name, final MonthDay monthDay) {
        this.name = name;
        this.monthDay = monthDay;
    }

    public static boolean isHoliday(final LocalDate localDate) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> Objects.equals(holiday.monthDay, MonthDay.from(localDate)) || isWeekend(localDate));
    }


    private static boolean isWeekend(final LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
