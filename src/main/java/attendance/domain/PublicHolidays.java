package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;

public enum PublicHolidays {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate localDate;

    PublicHolidays(final LocalDate localDate) {
        this.localDate = localDate;
    }

    public static void checkPublicHolidays(final LocalDate currentDate) {
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
        if (currentDayOfWeek.equals(DayOfWeek.SATURDAY) || currentDayOfWeek.equals(DayOfWeek.SUNDAY)
                || PublicHolidays.CHRISTMAS.localDate.equals(currentDate)) {
            throw CustomException.from(ErrorMessage.NOT_ATTENDANCE_WEEKEND);
        }
    }

}
