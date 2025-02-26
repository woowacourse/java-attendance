package dto;

public record ModifyAttendanceResponse(
        String date,
        String originalTime,
        String modifiedTime,
        String originalStatus,
        String modifiedStatus
) {
}
