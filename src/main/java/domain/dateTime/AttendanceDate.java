package domain.dateTime;

import controller.AttendanceCommandController;
import java.time.DateTimeException;
import java.time.LocalDate;

public class AttendanceDate {

    private final LocalDate date;

    private AttendanceDate(final LocalDate date) {
        AttendanceDateValidator.validate(date);
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

    public LocalDate getDate() {
        return date;
    }
}
