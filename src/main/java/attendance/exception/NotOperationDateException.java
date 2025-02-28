package attendance.exception;

import static attendance.exception.ErrorMessage.NOT_OPERATION_DATE;

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
        String message = NOT_OPERATION_DATE.getMessage();
        return message.formatted(date.getMonthValue(),
                date.getDayOfMonth(),
                day.getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
