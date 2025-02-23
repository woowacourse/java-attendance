package domain;

import global.util.Date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE,
    TARDY,
    ABSENCE;

    public static AttendanceStatus attend(LocalDateTime target) {
        LocalDate targetDate = target.toLocalDate();

        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalTime targetTime = target.toLocalTime();
        if (Date.isNotWorkingDay(targetDate)) {
            throw new IllegalArgumentException();
        }

        if (Date.isMonday(targetDate)) {
            attendanceTime = LocalTime.of(13, 0);
        }
        return getAttendanceStatusByTime(attendanceTime, targetTime);

    }

    private static AttendanceStatus getAttendanceStatusByTime(LocalTime attendanceTime, LocalTime targetTime) {
        if (isAbsenceTime(attendanceTime, targetTime)) {
            return ABSENCE;
        }
        if (isTardyTime(attendanceTime, targetTime)) {
            return TARDY;
        }
        return ATTENDANCE;
    }

    private static boolean isAbsenceTime(LocalTime attendanceTime, LocalTime targetTime) {
        return attendanceTime.plusMinutes(30).isBefore(targetTime) && targetTime.isAfter(attendanceTime);
    }

    private static boolean isTardyTime(LocalTime attendanceTime, LocalTime targetTime) {
        return attendanceTime.plusMinutes(5).isBefore(targetTime) && targetTime.isAfter(attendanceTime);
    }
}
