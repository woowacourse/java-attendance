package util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeUtil {
    public static int getYearBy(LocalDate localDate) {
        return localDate.getYear();
    }

    public static int getMonthBy(LocalDate localDate) {
        return localDate.getMonthValue();
    }

    public static int getDateBy(LocalDate localDate) {
        return localDate.getDayOfMonth();
    }

    public static String getDayOfWeekBy(LocalDate localDate) {
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static LocalDate getTodayLocalDate() {
        return LocalDate.of(2024, 12, 16);
    }

    public static int getTodayDate() {
        return 16;
//        return getDateBy(LocalDate.now());
    }
}
