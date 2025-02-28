package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDate {

    private final int year;
    private final int month;
    private final int day;

    public AttendanceDate(
        final int year,
        final int month,
        final int day
    ) {
        validateDate(month, day);
        validate2024December(year, month, day);
        validateHoliday(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void validateDate(
        final int month,
        final int day
    ) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("월은 1 이상 12 이하여야 합니다.");
        }

        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("일은 1 이상 31 이하여야 합니다.");
        }
    }

    private void validate2024December(
        final int year,
        final int month,
        final int day
    ) {
        if (year != 2024 || month != 12 || day > 31) {
            throw new IllegalArgumentException("출석 날짜는 2024년 12월만 지원합니다.");
        }
    }

    private void validateHoliday(
        final int year,
        final int month,
        final int day
    ) {
        if (isHoliday(year, month, day)) {
            throw new IllegalArgumentException("출석 날짜는 주말 또는 공휴일은 지원하지 않습니다.");
        }
    }

    private boolean isHoliday(
        final int year,
        final int month,
        final int day
    ) {
        return isWeekend(year, month, day) || isPublicHoliday(month, day);
    }

    private boolean isWeekend(
        final int year,
        final int month,
        final int day
    ) {
        return DayOfWeek.of(LocalDate.of(year, month, day)
                .getDayOfWeek()
                .getValue())
            .getValue() > 5;
    }

    private boolean isPublicHoliday(
        final int month,
        final int day
    ) {
        return month == 12 && day == 25;
    }

    public AttendanceDayOfWeek getAttendanceDayOfWeekDayOfWeek() {
        return AttendanceDayOfWeek.from(LocalDate.of(year, month, day));
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
