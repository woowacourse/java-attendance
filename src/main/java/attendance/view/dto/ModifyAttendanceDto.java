package attendance.view.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ModifyAttendanceDto(String status, LocalDateTime attendanceDateTime) {

    public LocalTime getTime() {
        return attendanceDateTime.toLocalTime();
    }
}
