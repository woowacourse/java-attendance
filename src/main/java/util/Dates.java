package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Dates {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    public static LocalTime DEFAULT_TIME = LocalTime.of(23, 59);
    public static LocalDate TODAY = LocalDate.of(2024, 12, 17);


    public static boolean isHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return isWeekend(day) || isChristmas(date);
    }

    public static boolean isNotHoliday(LocalDate date) {
        return !isHoliday(date);
    }

    private static boolean isChristmas(LocalDate date) {
        return Objects.equals(date, CHRISTMAS);
    }

    private static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static int getWorkingDays() {
        return (int) LocalDate.of(TODAY.getYear(), TODAY.getMonth(), 1)
                .datesUntil(TODAY.plusDays(1))
                .filter(Dates::isNotHoliday)
                .count();
    }
}
