package domain;

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

    public static boolean has(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();

        for (Holiday value : values()) {
            if (value.month == month && value.day == day) {
                return true;
            }
        }
        return false;
    }
}
