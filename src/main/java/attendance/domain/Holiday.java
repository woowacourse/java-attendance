package attendance.domain;

import java.time.MonthDay;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(MonthDay.of(12, 25));

    private final MonthDay monthDay;

    Holiday(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public static boolean isHoliday(MonthDay monthDay) {
        return Arrays.stream(values())
                .anyMatch(holiday -> holiday.monthDay.equals(monthDay));
    }
}
