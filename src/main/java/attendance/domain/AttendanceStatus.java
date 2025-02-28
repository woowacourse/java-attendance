package attendance.domain;

import java.time.LocalDateTime;

public enum AttendanceStatus {

    ATTEND,
    LATE,
    ABSENCE;

    public static AttendanceStatus of(final LocalDateTime attendanceDateTime) {
        int startHour = Attendance.checkStartHour(attendanceDateTime);
        if(attendanceDateTime.getHour() > startHour || (attendanceDateTime.getHour() >= 10 && attendanceDateTime.getMinute() > 30)) {
            return ABSENCE;
        }
        if(attendanceDateTime.getHour() == startHour && attendanceDateTime.getMinute() > 5) {
            return LATE;
        }
        return ATTEND;
    }
}
