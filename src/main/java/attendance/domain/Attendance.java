package attendance.domain;

import java.time.LocalDate;

public record Attendance(NullableLocalTime time, AttendanceStatus status) {
    public Attendance(LocalDate date, NullableLocalTime time) {
        this(time, AttendanceStatus.of(date, time));
    }
}
