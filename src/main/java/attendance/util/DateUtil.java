package attendance.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {

    private DateUtil() {}

    public static boolean isWeekend(LocalDate inputDate) {
        DayOfWeek dayOfWeekOfInputDate = inputDate.getDayOfWeek();

        return dayOfWeekOfInputDate == DayOfWeek.SATURDAY || dayOfWeekOfInputDate == DayOfWeek.SUNDAY;
    }
}
