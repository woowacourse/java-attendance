package dto;

import java.util.List;

public record GetAttendanceRecordsResponse(
        List<AttendanceRecord> attendanceRecords
) {
    private record AttendanceRecord(
            String date,
            String time,
            String status
    ) {
    }
}
