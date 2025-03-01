package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holidays {
    CHRISTMAS(LocalDate.of(2024, 12, 25)),
    ;

    private final LocalDate date;

    Holidays(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(Holidays.values())
                .anyMatch(holiday -> holiday.date.equals(date));
    }

    public static boolean isNotHoliday(LocalDate date) {
        return !isHoliday(date);
    }
}
