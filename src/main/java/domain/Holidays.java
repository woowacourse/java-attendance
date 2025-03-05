package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holidays {

    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holidays(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate today) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.date.equals(today));
    }
}
