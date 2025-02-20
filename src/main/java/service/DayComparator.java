package service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DayComparator {
    private static final int CHRISTMAS = 25;

    //TODO : 미래 날짜인지 확인
    public static boolean isHoliday(int date, LocalDateTime today) {
        LocalDate targetDate = LocalDate.of(today.getYear(), today.getMonth(), date);
        if (date == CHRISTMAS) {
            return true;
        }
        return targetDate.getDayOfWeek().getValue() == 6 || targetDate.getDayOfWeek().getValue() == 7;
    }

    public static boolean isHoliday(LocalDateTime today) {
        if (today.getDayOfMonth() == CHRISTMAS) {
            return true;
        }
        return today.getDayOfWeek().getValue() == 6 || today.getDayOfWeek().getValue() == 7;
    }
}
