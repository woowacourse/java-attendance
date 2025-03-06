package domain.policy.attend.date;

import java.time.MonthDay;
import java.util.stream.Stream;

public enum HolidayRule {

    CHRISTMAS(12, 25, "크리스마스");

    private final int month;
    private final int dayOfMonth;
    private final String description;

    HolidayRule(int month, int dayOfMonth, String description) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.description = description;
    }

    public static boolean isHoliday(MonthDay monthDay) {
        return Stream.of(HolidayRule.values())
                .map(holidayRule -> MonthDay.of(holidayRule.month, holidayRule.dayOfMonth))
                .anyMatch(Holiday -> Holiday.equals(monthDay));
    }

    public String getDescription() {
        return description;
    }
}
