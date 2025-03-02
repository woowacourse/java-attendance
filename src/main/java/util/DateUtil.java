package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class DateUtil {
    public static List<Integer> getAttendAbleDates(int day) {
        return Stream.iterate(1, i -> i + 1)
                .limit(day)
                .filter(DateUtil::isWeekdays)
                .filter(i -> i != 25)
                .toList();
    }

    private static boolean isWeekdays(int day) {
        DayOfWeek dayOfWeek = LocalDate.of(2024, 12, day)
                .getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY);
    }
}
