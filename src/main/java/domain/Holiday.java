package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS("크리스마스", 12, 25),
    NOT_HOLIDAY("공휴일이 아닌 경우", 0, 0),
    ;

    private final String description;
    private final int month;
    private final int day;

    Holiday(String description, int month, int day) {
        this.description = description;
        this.month = month;
        this.day = day;
    }

    public static Holiday from(LocalDate date) {
        return Arrays.stream(values())
                .filter(holiday -> holiday.month == date.getMonthValue() && holiday.day == date.getDayOfMonth())
                .findAny()
                .orElse(NOT_HOLIDAY);
    }
}
