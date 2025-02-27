package domain;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class CampusDate {

    private final LocalDate date;

    private CampusDate(final LocalDate date) {
        validateDateIsWeekday(date);
        this.date = date;
    }

    public static CampusDate now() {
        return new CampusDate(LocalDate.now());
    }

    public static CampusDate fromDate(final LocalDate date) {
        return new CampusDate(date);
    }

    public static CampusDate ofNowAndDay(final LocalDate now, final int day) {
        validateDayRangeOfMonth(now, day);
        return new CampusDate(now.withDayOfMonth(day));
    }

    private static void validateDayRangeOfMonth(final LocalDate now, final int day) {
        try {
            now.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 해당 월의 가능한 일 수 내에서 입력해 주세요.");
        }
    }

    private void validateDateIsWeekday(final LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 캠퍼스에 출석할 수 없습니다.");
        }
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }
}
