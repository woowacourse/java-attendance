package model;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate localDate;

    Holiday(final LocalDate localDate) {
        this.localDate = localDate;
    }

    public static boolean isHoliday(final LocalDate date) {
        return Arrays.stream(values())
                .anyMatch(value -> value.localDate.equals(date));
    }
}
