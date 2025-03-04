package utils;

import java.time.DayOfWeek;

public class DayOfWeekUtils {

    private DayOfWeekUtils(){
    }

    public static boolean isWeekend(final DayOfWeek dayOfWeek){
        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }
}
