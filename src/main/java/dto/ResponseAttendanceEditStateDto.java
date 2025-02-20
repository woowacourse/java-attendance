package dto;

import domain.attendance.AttendanceState;

public record ResponseAttendanceEditStateDto(String beforeDateTime,
                                             AttendanceState beforeState,
                                             String afterDateTime,
                                             AttendanceState afterState) {
}
