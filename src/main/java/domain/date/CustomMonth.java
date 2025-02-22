package domain.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum CustomMonth {
    JANUARY(31, 1),
    FEBRUARY(29, 2),
    MARCH(31, 3),
    APRIL(30, 4),
    MAY(31, 5),
    JUNE(30, 6),
    JULY(31, 7),
    AUGUST(31, 8),
    SEPTEMBER(30, 9),
    OCTOBER(31, 10),
    NOVEMBER(30, 11),
    DECEMBER(31, 12),
    ;

    private final int lastDay;

    private final int value;

    CustomMonth(int lastDay, int value) {
        this.lastDay = lastDay;
        this.value = value;
    }

    public static CustomMonth of(int monthValue) {
        return Arrays.stream(CustomMonth.values())
                .filter(month -> month.value == monthValue)
                .findFirst()
                .orElseThrow();
    }

    public boolean isHolidayAt(int date) {
        if (this == DECEMBER && date == 25) {
            return true;
        }
        DayOfWeek dayOfWeek = LocalDate.of(CustomDate.YEAR, CustomDate.CUSTOM_MONTH.getValue(), date).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public int getValue() {
        return value;
    }
}
