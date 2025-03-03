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

    public static boolean check(LocalDate date) {
        return Arrays.stream(values())
            .anyMatch(holiday -> holiday.isHoliday.test(date));
    }

    public static void validateWeekDay(LocalDate date) {
        boolean isHoliday = check(date);
        if (isHoliday) {
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.", DateConverter.convertToString(date)));
        }
    }
}
