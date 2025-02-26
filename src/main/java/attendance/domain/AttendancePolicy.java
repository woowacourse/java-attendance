package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AttendancePolicy {
    private static final LocalTime MONDAY_ABSENCE_THRESHOLD = LocalTime.of(13, 30);
    private static final LocalTime MONDAY_LATE_THRESHOLD = LocalTime.of(13, 05);
    private static final LocalTime NOT_MONDAY_ABSENCE_THRESHOLD = LocalTime.of(10, 30);
    private static final LocalTime NOT_MONDAY_LATE_THRESHOLD = LocalTime.of(10, 05);

    public static String calculateAttendanceStatus(DayOfWeek attendanceDay, LocalTime attendanceTime) {
        if (attendanceDay == DayOfWeek.MONDAY) {
            if (attendanceTime.isAfter(MONDAY_ABSENCE_THRESHOLD)) {
                return "ABSENCE";
            }
            if (attendanceTime.isAfter(MONDAY_LATE_THRESHOLD)) {
                return "LATE";
            }
            return "ATTENDANCE";
        }
        if (attendanceTime.isAfter(NOT_MONDAY_ABSENCE_THRESHOLD)) {
            return "ABSENCE";
        }
        if (attendanceTime.isAfter(NOT_MONDAY_LATE_THRESHOLD)) {
            return "LATE";
        }
        return "ATTENDANCE";
    }
}
