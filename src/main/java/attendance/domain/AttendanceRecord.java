package attendance.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRecord {
    private LocalDateTime attendanceDateTime;

    public AttendanceRecord(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        LocalTime startTime;
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = LocalTime.of(13, 0);
        } else {
            startTime = LocalTime.of(10, 0);
        }
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        Duration duration = Duration.between(startTime, attendanceTime);
        long minutes = duration.toMinutes();

        return AttendanceStatus.from(minutes);
    }
}
