package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeUtil {
//    private static LocalDate now() {
//        return LocalDate.now();
//    }

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

    public static int getTodayDate() {
        return getDateBy(LocalDate.now());
    }


    public static void validateHolyDay(final int date) {
        LocalDate localDate = LocalDate.of(2024, 12, date);
        if (DateTimeUtil.isHoliday(localDate)) {
            throw new IllegalArgumentException("공휴일에는 출석을 할 수 없습니다.");
        }
    }

    public static boolean isHoliday(LocalDate localDate) {
        return localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
