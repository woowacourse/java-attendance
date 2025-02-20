package attendance.controller.validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class HolidayValidator {
    public static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    public static final List<DayOfWeek> WEEKENDS = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public static void validate(LocalDate day) {
        if (WEEKENDS.contains(day.getDayOfWeek()) || day.equals(CHRISTMAS)) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    day.getMonthValue(),
                    day.getDayOfMonth(),
                    day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)));
        }
    }
}
