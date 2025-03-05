package model.policy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import model.Holiday;

public class CampusOperatingPolicy {

    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    public static boolean isOperatingDate(final LocalDate date) {
        return !isWeekend(date.getDayOfWeek()) && !Holiday.isHoliday(date);
    }

    public static boolean isOperatingTime(final LocalTime time) {
        return !time.isBefore(CAMPUS_OPEN_TIME) && !time.isAfter(CAMPUS_CLOSE_TIME);
    }

    private static boolean isWeekend(final DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
