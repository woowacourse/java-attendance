package service.dto;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import java.time.LocalDateTime;

public record SaveAttendanceRecordResponse(
        LocalDateTime dateTime,
        AttendanceStatus status
) {

    public static SaveAttendanceRecordResponse of(AttendanceRecord attendanceRecord) {
        return new SaveAttendanceRecordResponse(attendanceRecord.getDateTime(), attendanceRecord.getStatus());
    }
}
