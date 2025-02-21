package util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class TodayDateTimeUtil {
    private static LocalDate now() {
        return LocalDate.now();
    }

    public static int nowMonth() {
        return now().getMonthValue();
    }

    public static int nowDate() {
        return 19;
//        return now().getDayOfMonth();
    }

    public static String nowDayOfWeek() {
        return now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }
}
