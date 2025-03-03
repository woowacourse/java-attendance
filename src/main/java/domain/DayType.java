package domain;

import java.time.LocalDate;
import java.util.List;

public enum DayType {
    WEEKEND,
    WEEKDAY,
    HOLIDAY;

    private static final List<Integer> holidays = List.of(25);
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;

    public static DayType calculateDayType(int day) {
        LocalDate dateTime = LocalDate.of(2024, 12, day);
        if (dateTime.getDayOfWeek().getValue() == SATURDAY || dateTime.getDayOfWeek().getValue() == SUNDAY) {
            return WEEKEND;
        }
        if (holidays.contains(day)) {
            return HOLIDAY;
        }
        return WEEKDAY;
    }
}
