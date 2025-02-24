package util;

import java.util.HashSet;
import java.util.Set;

public final class HolidayManager {

    private static final Set<Integer> HOLIDAYS = new HashSet<>();

    private HolidayManager() {
    }

    static {
        HOLIDAYS.add(25);
    }

    public static boolean isHoliday(final Integer dayOfMonth) {
        return HOLIDAYS.contains(dayOfMonth);
    }

    public static Set<Integer> getHOLIDAYS() {
        return HOLIDAYS;
    }
}
