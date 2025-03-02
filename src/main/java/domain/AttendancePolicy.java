package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendancePolicy {
    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);
    private static final List<Integer> HOLIDAY = List.of(25);

    public static boolean isInvalidTime(LocalTime time) {
        return time.isBefore(CAMPUS_START_TIME) || time.isAfter(CAMPUS_END_TIME);
    }

    public static boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY
                || HOLIDAY.contains(date.getDayOfMonth());
    }
}
