package dto;

public record UpdatedAttendanceSnapshotResponse(
        AttendanceResponse before,
        AttendanceResponse after
) {
}
