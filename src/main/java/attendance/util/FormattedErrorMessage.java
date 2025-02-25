package attendance.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public enum FormattedErrorMessage {
    INVALID_ATTENDANCE_ERROR("%d월 %d일 %s은(는) 등교일이 아닙니다.")
    ;

    private static final String PREFIX = "[ERROR]";

    private final String message;

    FormattedErrorMessage(String message) {
        this.message = message;
    }

    public String getDateFormatMessage(LocalDate date) {
        return PREFIX + attendanceErrorFormat(date);
    }

    private String attendanceErrorFormat(LocalDate date) {
        return message.formatted(
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
    }
}
