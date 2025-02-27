package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum DayOff {
    CHRISTMAS(12, 25),
    ;

    private final int month;
    private final int day;

    DayOff(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isDayOff(LocalDate date) {
        if (isWeekend(date)) {
            return true;
        }
        return Arrays.stream(values()).anyMatch(holiday -> {
            LocalDate converted = LocalDate.of(date.getYear(), holiday.month, holiday.day);
            return converted.equals(date);
        });
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
