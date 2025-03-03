package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AttendanceDate {

    private final LocalDate date;

    public AttendanceDate(final LocalDate date) {
        validate(date);
        this.date = date;
    }

    public static AttendanceDate from(final String input) {
        try {
            final LocalDate date = LocalDate.parse(input);
            return new AttendanceDate(date);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. ex) 2");
        }
    }

    public static void validate(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
            throw new IllegalArgumentException("주말은 출석하실 수 없습니다.");
        }
        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            throw new IllegalArgumentException("주말은 출석하실 수 없습니다.");
        }
        HolidayCalendar.validateHoliday(date);
    }
}
