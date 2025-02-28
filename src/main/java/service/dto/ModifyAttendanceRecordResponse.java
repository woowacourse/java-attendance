package service.dto;

import domain.AttendanceRecord;

public record ModifyAttendanceRecordResponse(
        AttendanceRecord before,
        AttendanceRecord after
) {
    
}
