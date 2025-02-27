package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceTimeStatus(Optional<LocalTime> time, AttendanceStatus status) {

    public AttendanceTimeStatus(LocalDateTime localDateTime) {
        this(Optional.of(localDateTime.toLocalTime()), AttendanceChecker.checkAttendance(localDateTime));
    }

    public AttendanceTimeStatus() {
        this(Optional.empty(), ABSENCE);
    }
}
