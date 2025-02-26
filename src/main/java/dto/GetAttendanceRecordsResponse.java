package dto;

import java.util.List;

public record GetAttendanceRecordsResponse(
        List<AttendanceRecordDTO> attendanceRecordDTOs
) {
}
