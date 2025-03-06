package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AttendanceRecord(LocalDate date, LocalTime time) {
    public AttendanceRecord(LocalDateTime localDateTime) {
        this(LocalDate.from(localDateTime), LocalTime.from(localDateTime));
    }
}
