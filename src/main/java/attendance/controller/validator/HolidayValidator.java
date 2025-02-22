package attendance.controller.validator;

import static attendance.constant.ErrorMessage.NOT_ATTEND_DAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class HolidayValidator {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public static void validate(LocalDate day) {
        if (WEEKENDS.contains(day.getDayOfWeek()) || day.equals(CHRISTMAS)) {
            throw new IllegalArgumentException(String.format(NOT_ATTEND_DAY.getMessage(),
                    day.getMonthValue(),
                    day.getDayOfMonth(),
                    day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)));
        }
    }
}
