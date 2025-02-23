package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS("12-25");

    private final String date;

    Holiday(String date) {
        this.date = date;
    }

    private static String parseDate(String date) {
        return Current.YEAR + "-" + date;
    }

    public static boolean isHoliday(LocalDate targetDate) {
        return Arrays.stream(Holiday.values())
                .map(holiday -> Holiday.parseDate(holiday.date))
                .map(LocalDate::parse)
                .anyMatch(targetDate::equals);
    }

    public LocalDate getLocalDate() {
        return LocalDate.parse(parseDate(this.date));
    }
}
