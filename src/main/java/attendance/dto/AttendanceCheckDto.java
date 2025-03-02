package attendance.dto;

import attendance.domain.Attendance;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceCheckDto(int month, int day, String dayOfWeek, String attendanceTime, String attendanceType) {
    private static final String TIME_FORMAT = "HH:mm";

    public static AttendanceCheckDto fromAttendance(Attendance attendance) {
        int month = attendance.getAttendanceDate().getMonthValue();
        int day = attendance.getAttendanceDate().getDayOfMonth();
        String dayOfWeek = attendance.getAttendanceDate().getDayOfWeek()
                .getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String attendanceTime = attendance.getAttendanceTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        String attendanceType = attendance.getAttendanceType();
        return new AttendanceCheckDto(month, day, dayOfWeek, attendanceTime, attendanceType);
    }
}
