package attendance.dto;

import attendance.domain.Attendance;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceCheckDto(int month, int day, String dayOfWeek, String attendanceTime,
                                 String attendanceType) {

    public static AttendanceCheckDto fromAttendance(Attendance attendance) {
        int month = attendance.getDate().getMonthValue();
        int day = attendance.getDate().getDayOfMonth();
        String dayOfWeek = attendance.getDate().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String attendanceTime = attendance.getTimeValue();
        String attendanceType = attendance.getType().toString();
        return new AttendanceCheckDto(month, day, dayOfWeek, attendanceTime, attendanceType);
    }
}
