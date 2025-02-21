package attendance.domain.dto;

import attendance.domain.AttendanceHistory;

public record CrewDismissDto(String nickname, AttendanceHistory attendanceHistory) {

}
