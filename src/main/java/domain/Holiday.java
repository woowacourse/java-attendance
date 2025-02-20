package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25),
    NONE(0, 0),
    ;

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public static Holiday from(LocalDate date) {
        return Arrays.stream(values())
            .filter(holiday -> holiday.month == date.getMonthValue() && holiday.day == date.getDayOfMonth())
            .findAny()
            .orElse(NONE);
    }
}
