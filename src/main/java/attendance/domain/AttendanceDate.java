package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate implements Comparable<AttendanceDate> {

    public static final AttendanceDate FIRST_DATE = new AttendanceDate(2024,
        new Month(12), new Day(2));

    private final int year;
    private final Month month;
    private final Day day;

    public AttendanceDate(
        final int year,
        final Month month,
        final Day day
    ) {
        validateNotNull(month, day);
        validate2024December(year, month, day);
        validateHoliday(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void validateNotNull(
        final Month month,
        final Day day
    ) {
        if (month == null || day == null) {
            throw new NullPointerException("출석 날짜는 달와 일을 가지고 있어야 합니다.");
        }
    }

    private void validate2024December(
        final int year,
        final Month month,
        final Day day
    ) {
        if (year != 2024 || month.getValue() != 12 || day.getValue() > 31) {
            throw new IllegalArgumentException("출석 날짜는 2024년 12월만 지원합니다.");
        }
    }

    private void validateHoliday(
        final int year,
        final Month month,
        final Day day
    ) {
        if (isHoliday(year, month, day)) {
            throw new IllegalArgumentException("출석 날짜는 주말 또는 공휴일은 지원하지 않습니다.");
        }
    }

    private boolean isHoliday(
        final int year,
        final Month month,
        final Day day
    ) {
        return isWeekend(year, month, day) || isPublicHoliday(month, day);
    }

    private boolean isWeekend(
        final int year,
        final Month month,
        final Day day
    ) {
        return DayOfWeek.of(LocalDate.of(year, month.getValue(), day.getValue())
                .getDayOfWeek()
                .getValue())
            .getValue() > 5;
    }

    private boolean isPublicHoliday(
        final Month month,
        final Day day
    ) {
        return month.getValue() == 12 && day.getValue() == 25;
    }

    public static AttendanceDate from(final LocalDate localDate) {
        return new AttendanceDate(localDate.getYear(),
            new Month(localDate.getMonthValue()),
            new Day(localDate.getDayOfMonth()));
    }

    public AttendanceDate plusDay() {
        LocalDate localDate = LocalDate.of(year, month.getValue(),
                day.getValue())
            .plusDays(1);

        while (isHoliday(localDate.getYear(),
            new Month(localDate.getMonthValue()),
            new Day(localDate.getDayOfMonth()))) {
            localDate = localDate.plusDays(1);
        }

        return AttendanceDate.from(localDate);
    }

    public boolean isBefore(final AttendanceDate o) {
        return LocalDate.of(year, month.getValue(), day.getValue())
            .isBefore(
                LocalDate.of(o.year, o.month.getValue(), o.day.getValue()));
    }

    public AttendanceDayOfWeek getAttendanceDayOfWeekDayOfWeek() {
        return AttendanceDayOfWeek.from(
            LocalDate.of(year, month.getValue(), day.getValue()));
    }

    public Month getMonth() {
        return month;
    }

    public Day getDay() {
        return day;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AttendanceDate that = (AttendanceDate) o;
        return year == that.year && month.equals(that.month) && day.equals(
            that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public int compareTo(final AttendanceDate o) {
        return LocalDate.of(this.year, this.month.getValue(),
                this.day.getValue())
            .compareTo(
                LocalDate.of(o.year, o.month.getValue(), o.day.getValue()));
    }
}
