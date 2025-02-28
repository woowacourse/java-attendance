package attendance.exception;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class NotOperationDateException extends IllegalArgumentException {
    public NotOperationDateException(final LocalDate date) {
        super(formatErrorMessage(date));
    }

    private static String formatErrorMessage(final LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return "[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.".formatted(date.getMonthValue(),
                date.getDayOfMonth(),
                day.getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
