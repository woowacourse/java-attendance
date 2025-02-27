package dto;

public record AttendanceStatusCountResponse(
        int attendCount,
        int lateCount,
        int absentCount
) {
}
