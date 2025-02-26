package attendance.domain;

import java.time.LocalDateTime;

public record AttendanceHistory(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
}
