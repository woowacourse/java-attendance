package attendance.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public enum Holiday {

    CHRISTMAS(Month.DECEMBER, 25),
    ;

    private final Month month;
    private final int date;

    Holiday(Month month, int date) {
        this.month = month;
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.isSame(date));
    }

    private boolean isSame(LocalDate date) {
        return this.month == date.getMonth() && this.date == date.getDayOfMonth();
    }
}
