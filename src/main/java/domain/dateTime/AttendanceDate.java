package domain.dateTime;

import controller.AttendanceCommandController;
import domain.HolidayCalendar;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDate {

    private final LocalDate date;

    private AttendanceDate(final LocalDate date) {
        validate(date);
        this.date = date;
    }

    public static AttendanceDate from(final String input) {
        try {
            final int date = Integer.parseInt(input);
            final LocalDate localDate = LocalDate.of(
                    AttendanceCommandController.REFERENCE_YEAR,
                    AttendanceCommandController.REFERENCE_MONTH,
                    date
            );
            return new AttendanceDate(localDate);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("날짜는 숫자로 입력해야 합니다");
        } catch (final DateTimeException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다. ex) 2월 30일은 존재하지 않습니다.");
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

    public LocalDate getDate() {
        return date;
    }
}
