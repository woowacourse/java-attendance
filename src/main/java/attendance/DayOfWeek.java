package attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public enum DayOfWeek {

    MONDAY("월요일", LocalTime.of(13, 0)),
    TUESDAY("화요일", LocalTime.of(10, 0)),
    WEDNESDAY("수요일", LocalTime.of(10, 0)),
    THURSDAY("목요일", LocalTime.of(10, 0)),
    FRIDAY("금요일", LocalTime.of(10, 0)),
    SATURDAY("토요일", null),
    SUNDAY("일요일", null);

    private static final int LATE_STANDARD = 5;
    private static final int ABSENCE_STANDARD = 30;

    private final String dayOfWeekName;
    private final LocalTime attendanceStartingTime;

    DayOfWeek(String dayOfWeekName, LocalTime attendanceStartingTime) {
        this.dayOfWeekName = dayOfWeekName;
        this.attendanceStartingTime = attendanceStartingTime;
    }

    public String decideAttendanceType(LocalTime attendanceTime) {
        if (attendanceTime.getHour() - attendanceStartingTime.getHour() > 0) {
            return "결석";
        }
        if (attendanceTime.getMinute() - attendanceStartingTime.getMinute() >= ABSENCE_STANDARD) {
            return "결석";
        }
        if (attendanceTime.getMinute() - attendanceStartingTime.getMinute() >= LATE_STANDARD) {
            return "지각";
        }
        return "출석";
    }

    public static DayOfWeek findDayOfWeek(LocalDate currentDate) {
        String findDayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
        return Arrays.stream(DayOfWeek.values())
            .filter(result -> findDayOfWeek.equals(result.dayOfWeekName))
            .findAny()
            .get();
    }
}
