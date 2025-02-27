package dto;

import java.util.List;

public record GetAttendanceRecordsResponse(
        List<AttendanceRecordDTO> attendanceRecordDTOs
) {
    public record AttendanceRecordDTO(
            String date,
            String time,
            String status
    ) {
    }
}
