package domain;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public enum HolidayCalendar {
    DECEMBER(Month.DECEMBER, List.of(25));

    private final Month month;
    private final List<Integer> holidays;

    HolidayCalendar(final Month month, final List<Integer> holidays) {
        this.month = month;
        this.holidays = holidays;
    }

    public static void validateHoliday(final LocalDate date) {
        final int day = date.getDayOfMonth();
        final HolidayCalendar holidayCalender = findByDate(date);

        if (holidayCalender.holidays.contains(day)) {
            throw new IllegalArgumentException("등교할 수 없습니다.");
        }
    }

    private static HolidayCalendar findByDate(final LocalDate localDate) {
        final Month month = localDate.getMonth();
        return Arrays.stream(HolidayCalendar.values())
                .filter(holidayCalendar -> holidayCalendar.month.equals(month))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("아직 준비되지 않은 달입니다."));
    }
}
