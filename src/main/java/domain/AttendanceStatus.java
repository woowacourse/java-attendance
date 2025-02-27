package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENT("출석", 0),
    TARDY("지각", 5),
    ABSENT("결석", 30);

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);


    private final String name;
    private final int thresholdMinute;

    AttendanceStatus(String name, int thresholdMinute) {
        this.name = name;
        this.thresholdMinute = thresholdMinute;
    }

    public static AttendanceStatus of(LocalDateTime attendanceDateTime) {
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkAttendanceByDay(attendanceDateTime.toLocalTime(), MONDAY_START_TIME);
        }
        return checkAttendanceByDay(attendanceDateTime.toLocalTime(), DEFAULT_START_TIME);
    }

    private static AttendanceStatus checkAttendanceByDay(LocalTime attendanceTime, LocalTime openTime) {
        if (attendanceTime.isAfter(openTime.plusMinutes(ABSENT.thresholdMinute))) {
            return ABSENT;
        }
        if (attendanceTime.isAfter(openTime.plusMinutes(TARDY.thresholdMinute))) {
            return TARDY;
        }
        return PRESENT;
    }
}
