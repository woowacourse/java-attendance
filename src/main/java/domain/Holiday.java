package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    TEMPORARY_HOLIDAY_1(LocalDate.of(2025, 2, 18)),
    TEMPORARY_HOLIDAY_2(LocalDate.of(2025, 2, 20)),
    TEMPORARY_HOLIDAY_3(LocalDate.of(2025, 2, 21));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.date.equals(date));
    }
}
