package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.AttendanceType;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceModifyDto(String originalTime, String originalType, int month, int day, String dayOfWeek,
                                  String newAttendanceTime, String newAttendanceType) {

    public static AttendanceModifyDto fromModifiedAttendance(String originalTime, AttendanceType originalType,
                                                             Attendance modifiedAttendance) {
        String originalTypeString = originalType.toString();
        int month = modifiedAttendance.getDate().getMonthValue();
        int day = modifiedAttendance.getDate().getDayOfMonth();
        String dayOfWeek = modifiedAttendance.getDate().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String newAttendanceTime = modifiedAttendance.getTimeValue();
        String newAttendanceType = modifiedAttendance.getType().toString();
        return new AttendanceModifyDto(originalTime, originalTypeString, month, day, dayOfWeek, newAttendanceTime,
                newAttendanceType);
    }
}
