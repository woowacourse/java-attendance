package dto.response;

public record AttendanceRecordResponse(
        String date,
        String time,
        String status
) {
}

