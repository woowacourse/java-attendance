package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private static final LocalTime MONDAY_START = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_START = LocalTime.of(10, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;

    public String checkAttendance(String name, LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalTime openTime;
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            openTime = MONDAY_START;
        } else {
            openTime = DEFAULT_START;
        }
        LocalTime lateThreshold = openTime.plusMinutes(5);
        LocalTime absentThreshold = openTime.plusMinutes(30);
        if (attendanceTime.isAfter(absentThreshold)) {
            return "결석";
        }
        if (attendanceTime.isAfter(lateThreshold)) {
            return "지각";
        }
        return "출석";
    }
}
