package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate implements Comparable<AttendanceDate> {

    public static final AttendanceDate FIRST_DATE = new AttendanceDate(
        2024, 12, 2);

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

    public static AttendanceDate from(final LocalDate localDate) {
        return new AttendanceDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth()
        );
    }

    public AttendanceDate plusDay() {
        LocalDate localDate = LocalDate.of(year, month, day)
            .plusDays(1);

        while (isHoliday(localDate.getYear(), localDate.getMonthValue(),
            localDate.getDayOfMonth())) {
            localDate = localDate.plusDays(1);
        }

        return AttendanceDate.from(localDate);
    }

    public boolean isBefore(final AttendanceDate o) {
        return LocalDate.of(year, month, day)
            .isBefore(LocalDate.of(o.year, o.month, o.day));
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

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AttendanceDate that = (AttendanceDate) o;

        return year == that.year && month == that.month && day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public int compareTo(final AttendanceDate o) {
        return LocalDate.of(this.year, this.month, this.day)
            .compareTo(LocalDate.of(o.year, o.month, o.day));
    }
}
