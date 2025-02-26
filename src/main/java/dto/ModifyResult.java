package dto;

import domain.Attendance;

public record ModifyResult(Attendance originalAttendance, Attendance modifiedAttendance) {
}
