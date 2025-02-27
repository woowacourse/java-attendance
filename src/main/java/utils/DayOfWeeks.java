package utils;

import java.time.DayOfWeek;

public class DayOfWeeks {

    private DayOfWeeks(){
    }

    public static boolean isWeekend(final DayOfWeek dayOfWeek){
        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }
}
