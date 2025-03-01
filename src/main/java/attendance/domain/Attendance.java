package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(LocalDateTime dateTime, AttendanceStatus state) {
    public Attendance(LocalDateTime dateTime) {
        this(dateTime, judgeStatus(dateTime));
    }

    private static AttendanceStatus judgeStatus(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        LocalTime schedule = Schedule.getSchedule(date);
        return AttendanceStatus.judgeStatus(time, schedule);
    }
}
