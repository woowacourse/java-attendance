package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> date.isEqual(holiday.date));
    }
}
