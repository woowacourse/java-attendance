package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class DecemberCalendar {
    public static final String WORKING_DAY = "근무일";
    public static final String HOLIDAY = "공휴일";

    private static final List<Integer> HOLIDAYS = List.of(25);

    public static String judgeWorkingDay(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            return DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        }
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return DayOfWeek.SUNDAY.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        }
        if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        }
        if (HOLIDAYS.contains(date.getDayOfMonth())) {
            return HOLIDAY;
        }
        return WORKING_DAY;
    }
}