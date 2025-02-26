package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private static final int TARDY_THRESHOLD_MINUTE = 5;
    private static final int ABSENT_THRESHOLD_MINUTE = 30;
    private static final LocalTime MONDAY_OPEN = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_OPEN = LocalTime.of(10, 0);

    public String checkAttendance(String nickname, LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkAttendanceByDay(attendanceTime, MONDAY_OPEN);
        }
        return checkAttendanceByDay(attendanceTime, DEFAULT_OPEN);
    }

    private String checkAttendanceByDay(LocalTime attendanceTime, LocalTime openTime) {
        if (attendanceTime.isAfter(openTime.plusMinutes(ABSENT_THRESHOLD_MINUTE))) {
            return "결석";
        }
        if (attendanceTime.isAfter(openTime.plusMinutes(TARDY_THRESHOLD_MINUTE))) {
            return "지각";
        }
        return "출석";
    }
}
