package domain;

import global.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE,
    TARDY,
    ABSENCE;

    private static final int ABSENCE_THRESHOLD_MINUTES = 30;
    private static final int TARDY_THRESHOLD_MINUTES = 5;

    public static AttendanceStatus attend(LocalDateTime target) {
        LocalDate targetDate = target.toLocalDate();

        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalTime targetTime = target.toLocalTime();
        if (DateUtil.isNotWorkingDay(targetDate)) {
            throw new IllegalArgumentException();
        }

        if (DateUtil.isMonday(targetDate)) {
            attendanceTime = LocalTime.of(13, 0);
        }
        return getAttendanceStatusByTime(attendanceTime, targetTime);

    }

    private static AttendanceStatus getAttendanceStatusByTime(LocalTime attendanceTime, LocalTime targetTime) {
        if (targetTime.isAfter(attendanceTime)) {
            return compareToSpecificTime(attendanceTime, targetTime);
        }
        return ATTENDANCE;
    }

    private static AttendanceStatus compareToSpecificTime(LocalTime attendanceTime, LocalTime targetTime) {
        if (attendanceTime.plusMinutes(ABSENCE_THRESHOLD_MINUTES).isBefore(targetTime)) {
            return ABSENCE;
        }
        if (attendanceTime.plusMinutes(TARDY_THRESHOLD_MINUTES).isBefore(targetTime)) {
            return TARDY;
        }
        return ATTENDANCE;
    }
}
