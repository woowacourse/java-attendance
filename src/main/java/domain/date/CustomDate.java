package domain.date;

import exception.CannotRegisterAttendanceException;
import java.time.LocalDateTime;

public class CustomDate {
    public static final int YEAR = 2024;
    public static final CustomMonth CUSTOM_MONTH = CustomMonth.DECEMBER;
    private static final int FIXED_DATE = 14;

    public static LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now();
        return now.withYear(YEAR).withMonth(CUSTOM_MONTH.getValue()).withDayOfMonth(FIXED_DATE);
    }

    public static void throwIfHolidy(LocalDateTime date) {
        CustomMonth month = CustomMonth.of(date.getMonthValue());
        if (month.isHolidayAt(date.getDayOfMonth())) {
            throw new CannotRegisterAttendanceException(date);
        }
    }
}
