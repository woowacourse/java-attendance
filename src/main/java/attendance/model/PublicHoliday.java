package attendance.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public enum PublicHoliday {

    CHRISTMAS(Month.DECEMBER, 25),
    ;

    private final Month month;
    private final int date;

    PublicHoliday(Month month, int date) {
        this.month = month;
        this.date = date;
    }

    private boolean isMatch(LocalDate baseDate) {
        return month == baseDate.getMonth()
                && date == baseDate.getDayOfMonth();
    }

    public static boolean isPublicHoliday(LocalDate baseDate) {
        return Arrays.stream(values())
                .anyMatch(publicHoliday -> publicHoliday.isMatch(baseDate));
    }
}
