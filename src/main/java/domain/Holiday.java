package domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25),
    NONE(0, 0);

    private int month;
    private int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate localDate) {
        return Arrays.stream(Holiday.values())
                .anyMatch(date -> date.month == localDate.getMonthValue() && date.day == localDate.getDayOfMonth());
    }
}
