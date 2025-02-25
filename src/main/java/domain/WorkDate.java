package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class WorkDate implements Comparable<WorkDate> {
    private final int year;
    private final int month;
    private final int day;

    public WorkDate(int year, int month, int day) {
        validateSize(year, month, day);
        validateHoliday(year, month, day);
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

    private void validateHoliday(int year, int month, int day) {
        if (isHoliday(year, month, day)) {
            throw new IllegalArgumentException("주말 또는 공휴일에는 출석할 수 없습니다.");
        }
    }

    private boolean isHoliday(int year, int month, int day) {
        return isWeekend(year, month, day) || isPublicHoliday(month, day);
    }

    private boolean isWeekend(int year, int month, int day) {
        DayOfWeek dayOfWeek = LocalDate.of(year, month, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isPublicHoliday(int month, int day) {
        return month == 12 && day == 25;
    }

    public static WorkDate from(LocalDate localDate) {
        return new WorkDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    public WorkDate plusDay() {
        LocalDate nextDate = LocalDate.of(year, month, day + 1);

        while (isHoliday(nextDate.getYear(), nextDate.getMonthValue(), nextDate.getDayOfMonth())) {
            nextDate = nextDate.plusDays(1);
        }

        return WorkDate.from(nextDate);
    }

    public boolean isAfter(LocalDate localDate) {
        return LocalDate.of(this.year, this.month, this.day)
                .isAfter(localDate);
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
    public int compareTo(WorkDate other) {
        return LocalDate.of(this.year, this.month, this.day)
                .compareTo(LocalDate.of(other.year, other.month, other.day));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkDate workDate = (WorkDate) o;
        return year == workDate.year && month == workDate.month && day == workDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
