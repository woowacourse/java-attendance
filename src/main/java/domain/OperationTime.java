package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationTime {

    public static boolean isContainsOperationTime(final LocalTime targetTime) {
        return !targetTime.isBefore(LocalTime.of(8, 0)) && !targetTime.isAfter(LocalTime.of(23, 0));
    }

    public static void checkIsOperationDate(final LocalDate targetDate) {
        if (!isOperationDate(targetDate)) {
            throw new IllegalArgumentException("운영일이 아닙니다.");
        }
    }

    public static boolean isOperationDate(final LocalDate targetDate) {
        return !(isWeekend(targetDate) || Holiday.isHoliday(targetDate));
    }

    public static boolean isOperationDate(final int targetDay) {
        LocalDate date = LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), targetDay);
        return isOperationDate(date);
    }

    private static boolean isWeekend(final LocalDate targetDate) {
        return targetDate.getDayOfWeek() == DayOfWeek.SUNDAY || targetDate.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}
