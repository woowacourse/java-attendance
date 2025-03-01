package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Objects;

enum ClassDayOff {
    CHRISTMAS(MonthDay.of(12, 25));

    private final MonthDay monthDay;

    ClassDayOff(final MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public static boolean isDayOff(final LocalDate localDate) {
        return isWeekend(localDate) || isHoliday(localDate);

    }

    private static boolean isHoliday(final LocalDate localDate) {
        return Arrays.stream(ClassDayOff.values())
                .anyMatch(classDayOff -> Objects.equals(classDayOff.monthDay, MonthDay.from(localDate)));
    }

    private static boolean isWeekend(final LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
