package domain;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private final MonthDay date;

    Holiday(final int month, final int day) {
        this.date = MonthDay.of(month, day);
    }

    public static boolean isHoliday(final LocalDate targetDate) {
        MonthDay targetDay = MonthDay.from(targetDate);
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.date.equals(targetDay));
    }
}
