package attendance.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateConverter {

    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter LOCAL_TIME_FORMAT =  DateTimeFormatter.ofPattern("HH:mm");
    public static final String TIME_STRING_FORMAT = "%d월 %d일 %s요일";
    public static final String ABSENCE_TIME_MESSAGE = "--:--";
    public static final LocalTime ABSENCE_TIME = LocalTime.of(22, 59, 59);

    private DateConverter() {}

    public static String convertToString(LocalDate date) {
        return String.format(TIME_STRING_FORMAT, date.getMonthValue(), date.getDayOfMonth(), convertDayOfWeek(date));
    }

    private static String convertDayOfWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }

    public static String convertToString(LocalTime time) {
        if (time.equals(ABSENCE_TIME)) {
            return ABSENCE_TIME_MESSAGE;
        }
        return time.format(LOCAL_TIME_FORMAT);
    }

    public static LocalDate convertToDate(String input) {
        return LocalDate.parse(input, LOCAL_DATE_TIME_FORMAT);
    }

    public static LocalTime convertToTime(String input) {
        return LocalTime.parse(input, LOCAL_DATE_TIME_FORMAT);
    }
}
