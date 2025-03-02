package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static global.utils.DateTimeUtil.FIXED_RUNNING_DATETIME;

public enum AttendanceStatus {
    ATTENDANCE,
    TARDY,
    ABSENCE,
    NONE;

    private final static int TARDY_THRESHOLD_MINUTES = 5;
    private final static int ABSENCE_THRESHOLD_MINUTES = 30;
    private final static LocalTime MONDAY_ATTENDANCE_REFERENCE_TIME = LocalTime.of(13, 0);
    private final static LocalTime NORMAL_ATTENDANCE_REFERENCE_TIME = LocalTime.of(10, 0);

    public static AttendanceStatus evaluateAttendanceNow() {
        return evaluateAttendance(FIXED_RUNNING_DATETIME);
    }

    public static AttendanceStatus evaluateAttendance(LocalDateTime dateTime) {
        return evaluateAttendance(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public static AttendanceStatus evaluateAttendance(LocalDate date, LocalTime time) {
        final LocalTime referenceTime = getAttendanceReferenceTime(date);

        if (time.isAfter(referenceTime.plusMinutes(ABSENCE_THRESHOLD_MINUTES))) {
            return ABSENCE;
        }
        if (time.isAfter(referenceTime.plusMinutes(TARDY_THRESHOLD_MINUTES))) {
            return TARDY;
        }
        return ATTENDANCE;
    }

    private static LocalTime getAttendanceReferenceTime(LocalDate date) {
        if (DayOfWeek.MONDAY.equals(date.getDayOfWeek())) {
            return MONDAY_ATTENDANCE_REFERENCE_TIME;
        }
        return NORMAL_ATTENDANCE_REFERENCE_TIME;
    }
}
