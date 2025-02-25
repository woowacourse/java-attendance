package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25),
    NON_HOLIDAY(0, 0);

    private int month;
    private int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static Holiday from(LocalDate localDate) {
        return Arrays.stream(Holiday.values())
                .filter(holiday -> holiday.month == localDate.getMonthValue()
                        && holiday.day == localDate.getDayOfMonth())
                .findFirst()
                .orElse(NON_HOLIDAY);
    }
}
