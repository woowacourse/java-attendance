package dto;

import domain.Attendance;

public record ModifyingResult(Attendance originalAttendance, Attendance modifiedAttendance) {
}
