package attendance.domain;

import attendance.utils.DateConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

public enum Holiday {

    WEEKEND(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY),
    CHRISTMAS(date -> date.isEqual(LocalDate.of(2024, 12, 25)));

    Holiday(Predicate<LocalDate> isHoliday) {
        this.isHoliday = isHoliday;
    }

    private final Predicate<LocalDate> isHoliday;

    public static void check(LocalDate date) {
        boolean isWeekendOrChristmas = Arrays.stream(values())
            .anyMatch(holiday -> holiday.isHoliday.test(date));
        if (isWeekendOrChristmas) {
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.", DateConverter.convertToString(date)));
        }
    }
}
