package util;

import java.util.HashSet;
import java.util.Set;

public class HolidayManager {

    private static final Set<Integer> HOLIDAYS = new HashSet<>();

    static {
        HOLIDAYS.add(25);
    }

    public static boolean isHoliday(Integer dayOfMonth) {
        return HOLIDAYS.contains(dayOfMonth);
    }

    public static Set<Integer> getHOLIDAYS() {
        return HOLIDAYS;
    }
}
