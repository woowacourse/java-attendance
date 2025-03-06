package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceResult {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENT("결석", 30);
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);


    private final String name;
    private final int standard;

    AttendanceResult(String name, int standard) {
        this.name = name;
        this.standard = standard;
    }

    public static AttendanceResult getAttendanceResult(LocalDateTime attendanceTime) {
        LocalTime startTime =
                (attendanceTime.getDayOfWeek() == DayOfWeek.MONDAY) ? MONDAY_START_TIME : DEFAULT_START_TIME;
        return checkAttendance(attendanceTime.toLocalTime(), startTime);
    }

    private static AttendanceResult checkAttendance(LocalTime attendanceTime, LocalTime openTime) {
        if (attendanceTime.isAfter(openTime.plusMinutes(ABSENT.standard))) {
            return ABSENT;
        }
        if (attendanceTime.isAfter(openTime.plusMinutes(LATE.standard))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public String getName() {
        return name;
    }
}
