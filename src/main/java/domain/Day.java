package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Day {
    private final LocalDate date;

    public Day(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    public static Day of(Integer dayOfMonth) {
        return new Day(LocalDate.of(2025, 2, dayOfMonth));
    }

    public LocalDate getDate() {
        return date;
    }

    private void validateDate(LocalDate date) {
        if (isWeekend(date) || isHoliday(date)) {
            throw new IllegalStateException("[ERROR] 등교일이 아닙니다.");
        }
    }

    private boolean isWeekend(LocalDate date) {
        return DayOfWeek.SATURDAY.equals(date.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(date.getDayOfWeek());
    }

    private boolean isHoliday(LocalDate date) {
        return Holiday.isHoliday(date);
    }

    public LocalTime getCriteriaTime() {
        return CustomDayOfWeek.getCriteriaTime(date);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Day day = (Day) o;
        return Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
