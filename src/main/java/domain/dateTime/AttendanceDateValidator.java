package domain.dateTime;

import domain.HolidayCalendar;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public final class AttendanceDateValidator {

    private AttendanceDateValidator() {
    }

    private static final Set<DayOfWeek> WEEKEND_DAYS = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public static void validate(final LocalDate date) {
        HolidayCalendar.validateHoliday(date);

        if (WEEKEND_DAYS.contains(date.getDayOfWeek())) {
            throw new IllegalArgumentException("주말은 출석할 수 없습니다.");
        }
    }
}
