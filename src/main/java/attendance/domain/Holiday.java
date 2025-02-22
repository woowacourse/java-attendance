package attendance.domain;

import java.time.LocalDate;

public enum Holiday {

    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate compareDate) {
        for (Holiday holiday : Holiday.values()) {
            if (holiday.date.isEqual(compareDate)) {
                return true;
            }
        }
        return false;
    }
}
