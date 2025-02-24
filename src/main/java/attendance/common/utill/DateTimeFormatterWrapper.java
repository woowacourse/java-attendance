package attendance.common.utill;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateTimeFormatterWrapper {
    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";

    private DateTimeFormatterWrapper() {
        throw new AssertionError(INVALID_STATE);
    }

    public static DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.KOREAN);
    }
}
