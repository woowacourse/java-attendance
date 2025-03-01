package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Attendance(LocalTime time, AttendanceStatus status) {
    public Attendance(LocalDate date, LocalTime time) {
        this(time, AttendanceStatus.of(date, time));
    }
}
