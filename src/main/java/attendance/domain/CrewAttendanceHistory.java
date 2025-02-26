package attendance.domain;

public record CrewAttendanceHistory(String nickname, AttendanceHistories attendanceHistories,
                                    AttendanceStatuses attendanceStatuses) {
}
