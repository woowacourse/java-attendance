package domain;

import exception.AppException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class CheckInDate implements Comparable<CheckInDate> {
    private final LocalDate checkInDate;

    private CheckInDate(LocalDate checkInDate) {
        validateSchoolDay(checkInDate);
        this.checkInDate = checkInDate;
    }

    public static CheckInDate of(LocalDate checkInDate) {
        return new CheckInDate(checkInDate);
    }

    public static CheckInDate of(int year, int month, int day) {
        return of(LocalDate.of(year, month, day));
    }

    private void validateSchoolDay(LocalDate checkInDate) {
        if (isWeekend(checkInDate)) {
            throw new AppException("주말에는 출석할 수 없습니다.");
        }
        if (Holidays.isHoliday(checkInDate)) {
            throw new AppException("공휴일에는 출석할 수 없습니다.");
        }
    }

    public static boolean isWeekend(LocalDate checkInDate) {
        return checkInDate.getDayOfWeek() == DayOfWeek.SATURDAY || checkInDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isNotWeekend(LocalDate checkInDate) {
        return !isWeekend(checkInDate);
    }

    public LocalDate toLocalDate() {
        return checkInDate;
    }

    public LocalTime getClassStartTime() {
        return ClassTime.getClassStartTime(checkInDate);
    }

    @Override
    public int compareTo(CheckInDate o) {
        return checkInDate.compareTo(o.checkInDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInDate that = (CheckInDate) o;
        return checkInDate.equals(that.checkInDate);
    }

    @Override
    public int hashCode() {
        return checkInDate.hashCode();
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }
}
