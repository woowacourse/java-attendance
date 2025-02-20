package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    UNATTEND("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceStatus findStatus(LocalDateTime attendanceDateTime) {
        LocalTime startTime = getStartTime(attendanceDateTime);
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();

        if (attendanceTime.isBefore(startTime.plusMinutes(5).plusSeconds(1)) ) {
            return ATTEND;
        }
        if (attendanceTime.isAfter(startTime.plusMinutes(5)) && attendanceTime.isBefore(startTime.plusMinutes(30).plusSeconds(1))) {
            return LATE;
        }
        return ABSENT;
    }

    private static LocalTime getStartTime(LocalDateTime attendanceDateTime) {
        LocalTime startTime = LocalTime.of(10, 0);
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = LocalTime.of(13, 0);
        }
        return startTime;
    }
}
