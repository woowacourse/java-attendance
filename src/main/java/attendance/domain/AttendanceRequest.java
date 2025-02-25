package attendance.domain;

import java.time.LocalDateTime;

public record AttendanceRequest(CrewName crewName, LocalDateTime attendanceDateTime) {
}
