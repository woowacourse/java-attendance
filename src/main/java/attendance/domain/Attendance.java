package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(LocalDateTime dateTime, AttendanceStatus state) {
    public Attendance(LocalDateTime dateTime) {
        this(dateTime, judgeStatus(dateTime));
    }

    private static AttendanceStatus judgeStatus(LocalDateTime dateTime) {
        var status = AttendanceStatus.judgeStatus(dateTime.toLocalTime(), LocalTime.of(10, 0));
        return status;
    }
}
