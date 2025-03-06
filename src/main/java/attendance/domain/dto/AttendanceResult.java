package attendance.domain.dto;

import attendance.domain.Attendance;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceResult(
        int attendanceMonth,
        int attendanceDay,
        String attendanceDayOfWeek,
        int attendanceHour,
        int attendanceMinute,
        String attendanceStatus
) {
    public static AttendanceResult from(Attendance attendance) {
        return new AttendanceResult(
                attendance.getAttendanceDate().getMonthValue(),
                attendance.getAttendanceDate().getDayOfMonth(),
                attendance.getAttendanceDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.getAttendanceTime().getHour(),
                attendance.getAttendanceTime().getMinute(),
                attendance.getAttendanceStatus()
        );
    }

}
