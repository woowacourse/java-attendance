package attendance.dto;

import attendance.domain.Attendance;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceModifyDto(int month, int day, String dayOfWeek, String attendanceTime, String attendanceType,
                                  String newAttendanceTime, String newAttendanceType) {
    private static final String TIME_FORMAT = "HH:mm";

    public static AttendanceModifyDto fromOriginalToNewAttendance(final Attendance originalAttendance,
                                                                  final Attendance newAttendance) {
        int month = originalAttendance.getAttendanceDate().getMonthValue();
        int day = originalAttendance.getAttendanceDate().getDayOfMonth();
        String dayOfWeek = originalAttendance.getAttendanceDate().getDayOfWeek()
                .getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String attendanceTime = originalAttendance.getAttendanceTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        String attendanceType = originalAttendance.getAttendanceType();
        String newAttendanceTime = newAttendance.getAttendanceTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        String newAttendanceType = newAttendance.getAttendanceType();
        return new AttendanceModifyDto(month, day, dayOfWeek, attendanceTime, attendanceType, newAttendanceTime,
                newAttendanceType);
    }
}
