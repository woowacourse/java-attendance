package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public enum CampusHoliday {

    새해_첫날(1, 1),
    삼일절(3, 1),
    어린이날(5, 5),
    현충일(6, 6),
    광복절(8, 15),
    개천절(10, 3),
    한글일(10, 9),
    크리스마스(12, 25);

    private static final List<DayOfWeek> CAMPUS_CLOSED_DAY_OF_WEEK = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private final int month;
    private final int day;

    CampusHoliday(final int month, final int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isExistsInPublicHolidays(final LocalDate localDate) {
        CampusHoliday[] publicCampusHolidays = values();
        return Arrays.stream(publicCampusHolidays)
                .map(campusHoliday -> LocalDate.of(localDate.getYear(), campusHoliday.month, campusHoliday.day))
                .anyMatch(holidayDate -> holidayDate.isEqual(localDate));
    }

    public static boolean isCampusClosingDay(final LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return CAMPUS_CLOSED_DAY_OF_WEEK.contains(dayOfWeek);
    }

}
