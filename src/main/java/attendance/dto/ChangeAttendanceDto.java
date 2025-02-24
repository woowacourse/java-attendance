package attendance.dto;

import attendance.domain.Attendance;

public record ChangeAttendanceDto(Attendance originAttendance, Attendance newAttendance) {}
