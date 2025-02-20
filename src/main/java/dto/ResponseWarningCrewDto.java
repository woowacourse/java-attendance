package dto;

import domain.attendance.AttendanceState;
import domain.attendance.AttendanceWarning;

public record ResponseWarningCrewDto(String crewName,
                                     int absenceCount,
                                     int tardyCount,
                                     AttendanceWarning attendanceWarning) {
}
