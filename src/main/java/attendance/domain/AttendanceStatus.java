package attendance.domain;

import java.time.LocalDateTime;

public enum AttendanceStatus {

    ATTEND,
    LATE,
    ABSENCE;

    private static final int OVER_ABSENCE_MINUTE = 30;
    private static final int OVER_LATE_MINUTE = 5;

    public static AttendanceStatus of(final LocalDateTime attendanceDateTime) {
        int startHour = Attendance.checkStartHour(attendanceDateTime);
        if (attendanceDateTime.getHour() > startHour || (attendanceDateTime.getHour() >= startHour && attendanceDateTime.getMinute() > OVER_ABSENCE_MINUTE)) {
            return ABSENCE;
        }
        if (attendanceDateTime.getHour() == startHour && attendanceDateTime.getMinute() > OVER_LATE_MINUTE) {
            return LATE;
        }
        return ATTEND;
    }
}
