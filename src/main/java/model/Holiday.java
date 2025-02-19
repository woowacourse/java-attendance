package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum Holiday {

    CHRISTMAS(12, 25),
    ;

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHolidayOrWeekend(LocalDateTime time){
        return isHoliday(time) || isWeekend(time);
    }

    private static boolean isHoliday(LocalDateTime time) {
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();

        for(Holiday holiday : Holiday.values()){
            if(holiday.month == month && holiday.day == day){
                return true;
            }
        }
        return false;
    }

    private static boolean isWeekend(LocalDateTime checkInTime){
        DayOfWeek dayOfWeek = checkInTime.getDayOfWeek();

        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
