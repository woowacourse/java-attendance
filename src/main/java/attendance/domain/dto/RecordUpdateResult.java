package attendance.domain.dto;

import attendance.record.AttendanceRecord;

public record RecordUpdateResult(
        AttendanceRecord oldRecord,
        AttendanceRecord newRecord
) {

}
