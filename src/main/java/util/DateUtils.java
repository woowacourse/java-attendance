package util;

import java.time.DayOfWeek;

public class DateUtils {

    private DateUtils() {
    }

    public static boolean isWeekend(final DayOfWeek dayOfWeek) {
        return DayOfWeek.SUNDAY == dayOfWeek || DayOfWeek.SATURDAY == dayOfWeek;
    }
}
