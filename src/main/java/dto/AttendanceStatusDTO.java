package dto;

import domain.AttendanceDateTime;
import domain.AttendanceState;

public record AttendanceStatusDTO(
        AttendanceDateTime attendanceDateTime,
        AttendanceState attendanceState
) {
}
