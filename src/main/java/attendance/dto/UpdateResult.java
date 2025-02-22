package attendance.dto;

import attendance.domain.AttendanceRecord;

public record UpdateResult(
        AttendanceRecord oldRecord,
        AttendanceRecord newRecord
) {

}
