package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {

    새해_첫날(1, 1),
    삼일절(3, 1),
    어린이날(5, 5),
    현충일(6, 6),
    광복절(8, 15),
    개천절(10, 3),
    한글일(10, 9),
    크리스마스(12, 25);

    private final int month;
    private final int day;

    Holiday(final int month, final int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isExistsInPublicHolidays(final LocalDate localDate) {
        Holiday[] publicHolidays = values();
        return Arrays.stream(publicHolidays)
                .map(holiday -> LocalDate.of(localDate.getYear(), holiday.month, holiday.day))
                .anyMatch(holidayDate -> holidayDate.isEqual(localDate));
    }

    public static boolean isWeekend(final LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }

}
