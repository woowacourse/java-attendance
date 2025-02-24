package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    public final int month;
    public final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isWeekDay(LocalDate localDate) {
        if(!Holiday.isHoliday(localDate) && !Holiday.isWeekend(localDate)) {
            return true;
        }
        return false;
    }

    private static boolean isWeekend(LocalDate localDate) {
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return true;
        }
        if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    private static boolean isHoliday(LocalDate localDate) {
        return Arrays.stream(values())
            .anyMatch(holiday ->
                localDate.getMonthValue() == holiday.month
                    && localDate.getDayOfMonth() == holiday.day);
    }
}
