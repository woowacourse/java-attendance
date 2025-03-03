package util;

import domain.HolidayCalendar;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public final class DateUtil {

    private DateUtil() {
    }

    public static List<Integer> calculateValidDays(final int year, final int month, final int endOfDay) {
        return IntStream.range(1, endOfDay)
                .filter(day -> {
                    final LocalDate date = LocalDate.of(year, month, day);
                    final DayOfWeek dayOfWeek = date.getDayOfWeek();
                    return dayOfWeek != DayOfWeek.SATURDAY
                            && dayOfWeek != DayOfWeek.SUNDAY
                            && !HolidayCalendar.isHoliday(month, day);
                })
                .boxed()
                .toList();
    }
}
