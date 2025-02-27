package attendance.domain;

public record AttendanceUpdate(
        Attendance beforeAttendance,
        Attendance afterAttendance
) {

}
