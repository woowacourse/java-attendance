package dto.response;

public record CheckAttendanceResponse(
        String date,
        String time,
        String status
) {
}
