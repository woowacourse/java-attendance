package util;

import static model.AttendanceTime.DEFAULT_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDateTimeFormatter {

    public static final DateTimeFormatter csvDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private static final String DEFAULT_TIME_FORMAT = "--:--";

    public static String formatLocalDate(final LocalDate localDate) {
        return localDate.format(dateFormatter);
    }

    public static String formatLocalTime(final LocalTime localTime) {
        if (DEFAULT_TIME.equals(localTime)) {
            return DEFAULT_TIME_FORMAT;
        }
        return localTime.format(timeFormatter);
    }

}
