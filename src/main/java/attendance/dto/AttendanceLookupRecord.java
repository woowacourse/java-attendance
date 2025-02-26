package attendance.dto;

import attendance.domain.Attendance;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceLookupRecord(int month, int day, String dayOfWeek, String attendanceTime,
                                     String attendanceType) {

    public static AttendanceLookupRecord fromCrewAttendance(Attendance crewAttendance) {
        int month = crewAttendance.getDate().getMonthValue();
        int day = crewAttendance.getDate().getDayOfMonth();
        String dayOfWeek = crewAttendance.getDate().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String attendanceTime = crewAttendance.getTimeValue();
        String attendanceType = crewAttendance.getType().toString();
        return new AttendanceLookupRecord(month, day, dayOfWeek, attendanceTime, attendanceType);
    }
}
