package attendance.dto;

import attendance.domain.record.AttendanceRecord;

public record UpdateResult(
        AttendanceRecord oldRecord,
        AttendanceRecord newRecord
) {

}
