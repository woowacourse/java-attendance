package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25),
    NEW_YEAR(1,1),
    ;

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean has(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();

        return Arrays.stream(values())
                .anyMatch(value -> value.month == month && value.day == day);
    }

    public static boolean has(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return Arrays.stream(values())
                .anyMatch(value -> value.month == month && value.day == day);
    }
}
