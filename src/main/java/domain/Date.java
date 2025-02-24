package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class Date implements Comparable<Date> {
    private final int year;
    private final int month;
    private final int day;

    public Date(int year, int month, int day) {
        validateSize(year, month, day);
        validateDate(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void validateSize(int year, int month, int day) {
        if (year != 2024 || month != 12) {
            throw new IllegalArgumentException("2024년 12월이 아닌 날짜는 등록할 수 없습니다.");
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("일(day)은 1 이상 31 이하여야 합니다.");
        }
    }

    private void validateDate(int year, int month, int day) {
        if (isWeekend(year, month, day) || isPublicHoliday(month, day)) {
            throw new IllegalArgumentException("주말 또는 공휴일에는 출석할 수 없습니다.");
        }
    }

    private boolean isWeekend(int year, int month, int day) {
        return isWeekend(LocalDate.of(year, month, day));
    }

    private boolean isPublicHoliday(int month, int day) {
        return isPublicHoliday(LocalDate.of(2024, month, day));
    }

    public static Date from(LocalDate localDate) {
        return new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isPublicHoliday(LocalDate date) {
        return date.getMonthValue() == 12 && date.getDayOfMonth() == 25;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public WorkDay getWorkDay() {
        return WorkDay.from(LocalDate.of(year, month, day).getDayOfWeek());
    }

    @Override
    public int compareTo(Date other) {
        return LocalDate.of(this.year, this.month, this.day)
                .compareTo(LocalDate.of(other.year, other.month, other.day));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Date date = (Date) o;
        return year == date.year && month == date.month && day == date.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
