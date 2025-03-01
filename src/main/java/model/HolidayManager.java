package model;

import java.util.HashSet;
import java.util.Set;

public final class HolidayManager {

    private final static Set<Integer> holidays = new HashSet<>();

    static {
        holidays.add(25);
    }

    public static boolean isHoliday(final AttendanceDateTime attendanceDateTime) {
        final int dayOfMonth = attendanceDateTime.getDateTime().getDayOfMonth();
        return holidays.contains(dayOfMonth);
    }
}
