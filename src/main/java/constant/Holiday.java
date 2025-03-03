package constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(MonthDay.of(12, 25));

    private final MonthDay monthDay;

    Holiday(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public static boolean isHoliday(LocalDate localDate) {
        boolean isHoliday = Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.monthDay.getMonthValue() == localDate.getMonthValue()
                        && holiday.monthDay.getDayOfMonth() == localDate.getDayOfMonth());
        return isHoliday || isWeekend(localDate);
    }

    private static boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
