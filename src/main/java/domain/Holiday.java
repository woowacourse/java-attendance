package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private final LocalDate date;

    Holiday(final int month, final int day) {
        this.date = LocalDate.of(Current.TODAY.getYear(), month, day);
    }

    public static boolean isHoliday(final LocalDate targetDate) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.date.equals(targetDate));
    }
}
