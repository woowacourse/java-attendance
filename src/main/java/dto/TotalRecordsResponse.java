package dto;

public record TotalRecordsResponse(
        int attendanceCount,
        int lateCount,
        int absentCount
) {
}