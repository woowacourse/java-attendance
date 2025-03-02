package domain.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DateTimeUtil {
    public static final LocalDateTime FIXED_RUNNING_DATE = LocalDateTime.of(2024, 12, 14, 10, 0, 0);
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);
    public static final LocalTime START_RUNNING_TIME = LocalTime.of(8, 0);
    public static final LocalTime END_RUNNING_TIME = LocalTime.of(23, 0);

    public static final List<LocalDate> NATIONAL_HOLIDAYS = List.of(CHRISTMAS_DATE);

    public static DayOfWeek getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek();
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isHoliday(LocalDate date) {
        return NATIONAL_HOLIDAYS.contains(date);
    }

    public static boolean isWeekday(LocalDate date) {
        return !(isWeekend(date) || isHoliday(date));
    }

    public static boolean isOutOfRunningTime(LocalTime time) {
        return time.isAfter(END_RUNNING_TIME) || time.isBefore(START_RUNNING_TIME);
    }
}
