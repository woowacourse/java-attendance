package domain;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public enum Holiday {
    CHRISTMAS(12, 25);

    public int month;
    public int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate localDate) {
        if (localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN).equals("토")) {
            return true;
        }
        if (localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN).equals("일")) {
            return true;
        }

        for (Holiday holiday : values()) {
            if (localDate.getMonthValue() == holiday.month
                && localDate.getDayOfMonth() == holiday.day) {
                return true;
            }
        }
        return false;
    }

}
