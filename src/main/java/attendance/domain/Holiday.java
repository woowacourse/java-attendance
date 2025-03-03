package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public class Holiday {
    private final String name;
    private final int month;
    private final int day;

    public Holiday(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;
    }

    public boolean isMatch(LocalDate date) {
        if(date.getMonthValue() == month && date.getDayOfMonth() == day){
            return true;
        }
        return false;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
