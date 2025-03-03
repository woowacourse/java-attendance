package config;


import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    INDEPENDENCE_MOVEMENT_DAY_TEMPORARY(3, 3),
    CHILDREN_DAY(5, 5);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return Arrays.stream(values())
            .anyMatch(holiday ->
                holiday.month == month && holiday.day == day
            );
    }
}
