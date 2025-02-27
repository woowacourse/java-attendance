package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AttendanceStatusChecker {

    public static final LocalTime TUESDAY_TO_FRIDAY_ACCEPTABLE_ATTENDANCE_TIME = LocalTime.of(10, 0);
    public static final LocalTime MONDAY_ACCEPTABLE_ATTENDANCE_TIME = LocalTime.of(13, 0);
    public static final int ATTENDANCE_DEADLINE_MINUTE = 5;
    public static final int LATE_DEADLINE_MINUTE = 30;

    public enum AttendanceStatus {
        ATTENDANCE,
        LATE,
        ABSENT
    }

    public static AttendanceStatus checkStatus(AttendanceDateTime attendanceDateTime) {
        if (attendanceDateTime.calculateDayOfWeek().equals(DayOfWeek.MONDAY)) {
            int minuteDifference = attendanceDateTime.calculateMinuteDifference(MONDAY_ACCEPTABLE_ATTENDANCE_TIME);
            return findAttendanceStatusByMinuteDifference(minuteDifference);
        }
        int minuteDifference = attendanceDateTime.calculateMinuteDifference(TUESDAY_TO_FRIDAY_ACCEPTABLE_ATTENDANCE_TIME);
        return findAttendanceStatusByMinuteDifference(minuteDifference);
    }

    private static AttendanceStatus findAttendanceStatusByMinuteDifference(int minuteDifference) {
        if (minuteDifference > LATE_DEADLINE_MINUTE) {
            return AttendanceStatus.ABSENT;
        }
        if (minuteDifference > ATTENDANCE_DEADLINE_MINUTE) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
}
