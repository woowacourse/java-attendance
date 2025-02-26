package dto;

public record CheckAttendanceResponse(
        String date,
        String time,
        String status
) {
}
