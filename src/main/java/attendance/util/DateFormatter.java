package attendance.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFormatter {
    private static final String DATE_DAY_OF_WEEK_FORMAT = "%d월 %02d일 %s";

    public static String formatDate(LocalDate localDate) {
        return String.format(DATE_DAY_OF_WEEK_FORMAT,
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
