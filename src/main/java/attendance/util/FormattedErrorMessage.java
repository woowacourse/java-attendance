package attendance.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public enum FormattedErrorMessage {

    INVALID_ATTEND_DATE_ERROR("%d월 %d일 %s은 등교일이 아닙니다."),
    INVALID_ATTEND_TIME_ERROR("%02d시 %02d분은 운영 시간이 아닙니다.")
    ;

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    FormattedErrorMessage(String message) {
        this.message = message;
    }

    public String getDateFormatMessage(LocalDate date) {
        return PREFIX + dateErrorFormat(date);
    }

    private String dateErrorFormat(LocalDate date) {
        return message.formatted(
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
    }

    public String getTimeFormatMessage(LocalTime time) {
        return PREFIX + timeErrorFormat(time);
    }

    private String timeErrorFormat(LocalTime time) {
        return message.formatted(
                time.getHour(),
                time.getMinute()
        );
    }
}
