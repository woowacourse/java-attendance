package attendance.util;

import java.time.DayOfWeek;

public class DateUtils {

    private DateUtils() {
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
