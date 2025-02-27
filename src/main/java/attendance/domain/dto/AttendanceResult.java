package attendance.domain.dto;

import attendance.domain.Attendance;

public record AttendanceResult(
        int attendanceMonth,
        int attendanceDay,
        int attendanceHour,
        int attendanceMinute,
        String attendanceStatus
) {
    public static AttendanceResult from(Attendance attendance) {
        return new AttendanceResult(
                attendance.getAttendanceDate().getMonthValue(),
                attendance.getAttendanceDate().getDayOfMonth(),
                attendance.getAttendanceTime().getHour(),
                attendance.getAttendanceTime().getMinute(),
                attendance.getAttendanceStatus()
        );
    }

}
