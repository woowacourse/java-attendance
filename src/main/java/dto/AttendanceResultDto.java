package dto;

public record AttendanceResultDto(
        int attendCount,
        int lateCount,
        int absentCount,
        String crewStatus
) {
}
