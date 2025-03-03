package service.dto;

import domain.AbstractAttendanceRecord;
import domain.AttendanceRecord;

public record ModifyAttendanceRecordResponse(
        AbstractAttendanceRecord before,
        AttendanceRecord after
) {

}
