package domain.datetime;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CampusDate {

    private final LocalDate date;

    private CampusDate(final LocalDate date) {
        validateDateIsWeekday(date);
        this.date = date;
    }

    public static CampusDate from(final String date) {
        LocalDate localDate = validateDateRangeAndType(date);
        return new CampusDate(localDate);
    }

    public static CampusDate fromDate(final LocalDate date) {
        return new CampusDate(date);
    }

    public static CampusDate ofDateWithDay(final LocalDate date, final int day) {
        validateDayRangeOfMonth(date, day);
        return new CampusDate(date.withDayOfMonth(day));
    }

    public CampusDate withDay(final int day) {
        return new CampusDate(date.withDayOfMonth(day));
    }

    public boolean isSameDay(int day) {
        return date.getDayOfMonth() == day;
    }

    private static LocalDate validateDateRangeAndType(final String inputDate) {
        try {
            return LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 유효한 범위의 날짜를 입력해 주세요.");
        }
    }

    private static void validateDayRangeOfMonth(final LocalDate date, final int day) {
        try {
            date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 해당 월의 가능한 일 수 내에서 입력해 주세요.");
        }
    }

    private void validateDateIsWeekday(final LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 캠퍼스에 출석할 수 없습니다.");
        }
    }

    public int getYear() {
        return date.getYear();
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
