package attendance.utility;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateTimeFormatterWrapper {
    private DateTimeFormatterWrapper() {
    }

    public static DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.KOREAN);
    }
}
