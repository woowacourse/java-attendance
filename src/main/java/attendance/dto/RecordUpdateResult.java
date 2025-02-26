package attendance.dto;

import attendance.domain.record.AttendanceRecord;

public record RecordUpdateResult(
        AttendanceRecord oldRecord,
        AttendanceRecord newRecord
) {

}
