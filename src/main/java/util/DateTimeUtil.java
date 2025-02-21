package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeUtil {
    private static LocalDate now() {
        return LocalDate.now();
    }

    public static int getYearBy(LocalDate localDate) {
        return localDate.getYear();
    }

    public static int getMonthBy(LocalDate localDate) {
        return localDate.getMonthValue();
    }

    public static int getDateBy(LocalDate localDate) {
//        return 19;
        return localDate.getDayOfMonth();
    }

    public static String getDayOfWeekBy(LocalDate localDate) {
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static boolean isHoliday(LocalDate localDate) {
        return localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

}
