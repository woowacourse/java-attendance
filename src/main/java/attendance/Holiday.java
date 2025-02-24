package attendance;

import java.time.LocalDate;

public enum Holiday {

    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return false;
    }
}
