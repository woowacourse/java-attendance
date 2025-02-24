package domain.constants;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(final LocalDate date) {
        this.date = date;
    }


    public static boolean isHoliday(final LocalDate target) {
        return Arrays.stream(values())
                .map(Holiday::getDate)
                .filter(date -> isEqualsMonthAndDayOfMonth(date, target))
                .findAny()
                .isPresent();
    }

    private static boolean isEqualsMonthAndDayOfMonth(final LocalDate date, final LocalDate target) {
        return date.getMonthValue() == target.getMonthValue() && date.getDayOfMonth() == target.getDayOfMonth();
    }

    public LocalDate getDate() {
        return date;
    }
}
