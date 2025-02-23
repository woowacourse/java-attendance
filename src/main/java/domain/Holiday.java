package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private final int month;
    private final int date;

    Holiday(int month, int date) {
        this.month = month;
        this.date = date;
    }

    public static boolean isHoliday(LocalDate targetDate) {
        return Arrays.stream(Holiday.values())
                .map(holiday -> LocalDate.of(Current.YEAR, holiday.month, holiday.date))
                .anyMatch(targetDate::equals);
    }
}
