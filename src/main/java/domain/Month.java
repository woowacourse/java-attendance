package domain;

import constants.DateConstants;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public enum Month {
    JANUARY(31),
    FEBRUARY(29),
    MARCH(31),
    APRIL(30),
    MAY(31),
    JUNE(30),
    JULY(31),
    AUGUST(31),
    SEPTEMBER(30),
    OCTOBER(31),
    NOVEMBER(30),
    DECEMBER(31),
    ;

    private final int lastDay;

    Month(int lastDay) {
        this.lastDay = lastDay;
    }

    public boolean isHoliday(int day) {
        if (this == DECEMBER) {
            if (day == 25) {
                return true;
            }
        }
        DayOfWeek dayOfWeek = LocalDate.of(DateConstants.YEAR, DateConstants.MONTH, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public int getLastDay() {
        return lastDay;
    }
}
