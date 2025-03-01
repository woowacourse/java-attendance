package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public enum DayOfWeek  {

    MONDAY("월요일", LocalTime.of(13, 0)),
    TUESDAY("화요일", LocalTime.of(10, 0)),
    WEDNESDAY("수요일", LocalTime.of(10, 0)),
    THURSDAY("목요일", LocalTime.of(10, 0)),
    FRIDAY("금요일", LocalTime.of(10, 0)),
    SATURDAY("토요일", LocalTime.of(0, 0)),
    SUNDAY("일요일", LocalTime.of(0, 0));

    private final String dayOfWeekName;
    private final LocalTime attendanceStartingTime;

    DayOfWeek(String dayOfWeekName, LocalTime attendanceStartingTime) {
        this.dayOfWeekName = dayOfWeekName;
        this.attendanceStartingTime = attendanceStartingTime;
    }

    public static DayOfWeek findDayOfWeek(LocalDate currentDate) {
        String findDayOfWeek = currentDate.getDayOfWeek()
            .getDisplayName(TextStyle.FULL, Locale.KOREA);
        return Arrays.stream(DayOfWeek.values())
            .filter(result -> findDayOfWeek.equals(result.dayOfWeekName))
            .findAny()
            .get();
    }

    public int calculateTypeDecisionValueOnHour(AttendanceTime attendanceTime) {
        LocalDateTime attendanceDateTime = attendanceTime.getAttendanceDateTime();
        return attendanceDateTime.getHour() - attendanceStartingTime.getHour();
    }

    public int calculateTypeDecisionValueOnMinute(AttendanceTime attendanceTime) {
        LocalDateTime attendanceDateTime = attendanceTime.getAttendanceDateTime();
        return attendanceDateTime.getMinute() - attendanceStartingTime.getMinute();
    }
}
