package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    WOOTECO_START_DAY(LocalDate.of(2025, 2, 11));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean check(LocalDate date) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.date.equals(date));
    }
}
