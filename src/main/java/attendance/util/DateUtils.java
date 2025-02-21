package attendance.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DateUtils {

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
