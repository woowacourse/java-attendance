package global.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtil {
    public static LocalDateTime TODAY = LocalDateTime.of(LocalDate.of(2024, 12, 14), LocalTime.of(10, 30, 00));
    public static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public static LocalDate getFirstDateOfMonth() {
        return TODAY.toLocalDate()
                .withDayOfMonth(1);
    }

    public static boolean isNotWorkingDay(LocalDate localDate) {
        return localDate.isEqual(CHRISTMAS) || isWeekend(localDate);
    }

    public static boolean isWeekend(LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isWeekday(LocalDate localDate) {
        return !(isNotWorkingDay(localDate) || isWeekend(localDate));
    }

    public static LocalDateTime assembleDateAndTime(LocalDate localDate, LocalTime localTime) {
        return LocalDateTime.of(localDate, localTime);
    }

    public static LocalDate getDateByInputDay(int day) {
        return LocalDate.of(TODAY.getYear(), TODAY.getDayOfMonth(), day);
    }

    public static boolean isMonday(LocalDate targetDate) {
        return targetDate.getDayOfWeek() == DayOfWeek.MONDAY;
    }
}
