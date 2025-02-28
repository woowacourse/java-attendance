package attendance;

import static attendance.DayOfWeek.*;

import java.time.LocalDateTime;

public enum AttendanceType {
    ATTENDANCE, LATE, ABSENCE;

    public static AttendanceType decideAttendanceType(LocalDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = findDayOfWeek(attendanceDateTime.toLocalDate());
        if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
            throw new IllegalArgumentException();
        }

        if (!OperatingTime.isOperating(attendanceDateTime.toLocalTime())) {
            throw new IllegalArgumentException();
        }

        if ((attendanceDateTime.getHour() - dayOfWeek.getAttendanceStartingTime().getHour() > 0)) {
            return ABSENCE;
        }
        if (attendanceDateTime.getMinute() - dayOfWeek.getAttendanceStartingTime().getMinute() >= 30) {
            return ABSENCE;
        }
        if (attendanceDateTime.getMinute() - dayOfWeek.getAttendanceStartingTime().getMinute() >= 5) {
            return LATE;
        }
        return ATTENDANCE;
    }
}
