package domain;

import exception.InvalidMonthValueException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public enum Month {
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

    Month(int lastDay, int value) {
        this.lastDay = lastDay;
        this.value = value;
    }

    public static Month of(int month) {
        return Arrays.stream(values())
                .filter(m -> m.getValue() == month)
                .findFirst()
                .orElseThrow(InvalidMonthValueException::new);
    }

    public boolean isHoliday(int day) {
        if (this == DECEMBER) {
            if (day == 25) {
                return true;
            }
        }
        DayOfWeek dayOfWeek = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), day)
                .getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public int getLastDay() {
        return lastDay;
    }

    public int getValue() {
        return value;
    }
}
