package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    NEW_YEAR_DAY(LocalDate.of(2025, 1, 1)),
    CHRISTMAS_DAY(LocalDate.of(2025, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static Boolean isHoliday(LocalDate date) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.date.equals(date));
    }


}
