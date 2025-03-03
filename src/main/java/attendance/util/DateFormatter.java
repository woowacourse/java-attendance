package attendance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFormatter {
    private static final String DATE_DAY_OF_WEEK_FORMAT = "%d월 %02d일 %s";

    public static String formatDate(LocalDateTime target) {
        return formatDate(LocalDate.from(target));
    }

    public static String formatDate(LocalDate target) {
        return String.format(DATE_DAY_OF_WEEK_FORMAT,
                target.getMonthValue(),
                target.getDayOfMonth(),
                target.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
