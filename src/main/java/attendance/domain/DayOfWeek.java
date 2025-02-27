package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public enum DayOfWeek {

    MONDAY("월요일", LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUESDAY("화요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEDNESDAY("수요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    THURSDAY("목요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    FRIDAY("금요일", LocalTime.of(10, 0), LocalTime.of(18, 0)),
    SATURDAY("토요일", LocalTime.of(0, 0), LocalTime.of(0, 0)),
    SUNDAY("일요일", LocalTime.of(0, 0), LocalTime.of(0, 0));

    private static final int ABSENCE_TIME = 31;

    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;

    DayOfWeek(String name, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int calculateLateTime(LocalTime attendanceTime) {
        if (attendanceTime.getHour() - startTime.getHour() > 0) {
            return ABSENCE_TIME;
        }
        return attendanceTime.getMinute() - startTime.getMinute();
    }

    public static DayOfWeek calculateDayOfWeek(LocalDate localDate) {
        java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String displayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);
        return DayOfWeek.valueOf(displayName.toUpperCase());
    }

    public static boolean isWeekday(LocalDate localDate) {
        if (calculateDayOfWeek(localDate) == SATURDAY || calculateDayOfWeek(localDate) == SUNDAY) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }
}
