package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class DateUtil {
    private static final int CHRISTMAS = 25;

    public static List<Integer> getAttendAbleDates(int endDay) {
        return Stream.iterate(1, day -> day + 1)
                .limit(endDay)
                .filter(DateUtil::isWeekdays)
                .filter(i -> i != CHRISTMAS)
                .toList();
    }

    private static boolean isWeekdays(int day) {
        DayOfWeek dayOfWeek = LocalDate.of(Current.getYearOfToday(), Current.getMonthOfToday(), day)
                .getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY);
    }

    public static boolean isAttendAbleDate(int day) {
        LocalDate targetDate = LocalDate.of(Current.getYearOfToday(), Current.getMonthOfToday(), day);
        return isAttendAbleDate(targetDate);
    }

    private static boolean isAttendAbleDate(LocalDate targetDate) {
        boolean isWeekend = targetDate.getDayOfWeek()
                .getValue() >= 6;
        boolean isChristmas = targetDate.getDayOfMonth() == CHRISTMAS;
        return !(isWeekend || isChristmas);
    }
}
