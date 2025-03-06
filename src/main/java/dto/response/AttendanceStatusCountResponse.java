package dto.response;

public record AttendanceStatusCountResponse(
        int attendCount,
        int lateCount,
        int absentCount
) {
}
