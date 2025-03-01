package attendance.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String makeDateMessage(final LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }

    public static String makeTimeMessage(final LocalTime localTime) {
        return TIME_FORMATTER.format(localTime);
    }
}
