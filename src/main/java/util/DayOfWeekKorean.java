package util;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class DayOfWeekKorean {

    private static final Map<DayOfWeek, String> KOREAN_DAY_NAMES;

    private DayOfWeekKorean() {
    }

    static {
        final Map<DayOfWeek, String> map = new EnumMap<>(DayOfWeek.class);
        map.put(DayOfWeek.MONDAY, "월요일");
        map.put(DayOfWeek.TUESDAY, "화요일");
        map.put(DayOfWeek.WEDNESDAY, "수요일");
        map.put(DayOfWeek.THURSDAY, "목요일");
        map.put(DayOfWeek.FRIDAY, "금요일");
        map.put(DayOfWeek.SATURDAY, "토요일");
        map.put(DayOfWeek.SUNDAY, "일요일");
        KOREAN_DAY_NAMES = Collections.unmodifiableMap(map);
    }

    public static String getKoreanName(final DayOfWeek day) {
        return KOREAN_DAY_NAMES.get(day);
    }
}
