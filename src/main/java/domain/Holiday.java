package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {

    CHRISTMAS("크리스마스", 12, 25),
    ;

    private final String name;
    private final int month;
    private final int day;

    Holiday(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(int month, int day) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.month == month && holiday.day == day);
    }

    public static boolean isHoliday(LocalDate date) {
        return isHoliday(date.getMonthValue(), date.getDayOfMonth());
    }
}
