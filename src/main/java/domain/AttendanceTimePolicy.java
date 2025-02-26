package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public class AttendanceTimePolicy {

    private static final int LATE_MINUTES = 5;
    private static final int ABSENCE_MINUTES = 30;
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);

    private static final Map<DayOfWeek, LocalTime> START_TIMES = new EnumMap<>(DayOfWeek.class);

    static {
        START_TIMES.put(DayOfWeek.MONDAY, LocalTime.of(13, 0)); // 월요일만 다른 시간
    }

    public static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        return START_TIMES.getOrDefault(dayOfWeek, DEFAULT_START_TIME);
    }

    public static LocalTime getLateTime(DayOfWeek dayOfWeek) {
        return getStartTime(dayOfWeek).plusMinutes(LATE_MINUTES);
    }

    public static LocalTime getAbsenceTime(DayOfWeek dayOfWeek) {
        return getStartTime(dayOfWeek).plusMinutes(ABSENCE_MINUTES);
    }
}
