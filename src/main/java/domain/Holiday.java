package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25);

    private final int month;
    private final int day;
    private final LocalDate date;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
        this.date = createDate();
    }

    private LocalDate createDate() {
        return LocalDate.of(Current.YEAR, this.month, this.day);
    }

    public static boolean isHoliday(LocalDate targetDate) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.isEqualDate(targetDate));
    }

    private boolean isEqualDate(LocalDate localDate) {
        return this.date.isEqual(localDate);
    }
}
