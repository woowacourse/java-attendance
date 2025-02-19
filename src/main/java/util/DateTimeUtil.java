package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private final static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
    private final static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("hh시 mm분");

    public static int getDayOfWeek(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek().getValue();
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        String parsedLocalDate = localDateTime.format(localDateFormatter);
        String parsedLocalTime = localDateTime.format(localTimeFormatter);

        return parsedLocalDate + " " + convertDayOfWeekToString(localDateTime.getDayOfWeek().getValue()) + " " +
        parsedLocalTime;
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        String parsedLocalDate = localDate.format(localDateFormatter);
        return parsedLocalDate + " " + convertDayOfWeekToString(localDate.getDayOfWeek().getValue());
    }

    private static String convertDayOfWeekToString(int dayOfWeek) {
        if (dayOfWeek == 1) {
            return "월요일";
        }
        if (dayOfWeek == 2) {
            return "화요일";
        }
        if (dayOfWeek == 3) {
            return "수요일";
        }
        if (dayOfWeek == 4) {
            return "목요일";
        }
        if (dayOfWeek == 5) {
            return "금요일";
        }
        if (dayOfWeek == 6) {
            return "토요일";
        }
        return "일요일";
    }
}
