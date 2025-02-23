package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate holiday;

    Holiday(LocalDate holiday) {
        this.holiday = holiday;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.holiday.isEqual(date));
    }
}
