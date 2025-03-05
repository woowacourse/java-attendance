package domain;

public record AttendanceModification(
        AttendanceRecord beforeAttendanceRecord,
        AttendanceRecord afterAttendanceRecord
) {
}
