package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE,
    TARDY,
    ABSENCE,
    NONE;

    private final static int TARDY_THRESHOLD_MINUTES = 5;
    private final static int ABSENCE_THRESHOLD_MINUTES = 30;
    private final static LocalDateTime FIXED_RUNNING_DATE = LocalDateTime.of(2024, 12, 14, 10, 0, 0);
    private final static LocalTime MONDAY_ATTENDANCE_REFERENCE_TIME = LocalTime.of(13, 0);
    private final static LocalTime NORMAL_ATTENDANCE_REFERENCE_TIME = LocalTime.of(10, 0);

    public static AttendanceStatus evaluateAttendanceNow() {
        return evaluateAttendance(FIXED_RUNNING_DATE);
    }

    public static AttendanceStatus evaluateAttendance(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        final LocalTime referenceTime = getAttendanceReferenceTime(dateTime);

        if (time.isAfter(referenceTime.plusMinutes(ABSENCE_THRESHOLD_MINUTES))) {
            return ABSENCE;
        }
        if (time.isAfter(referenceTime.plusMinutes(TARDY_THRESHOLD_MINUTES))) {
            return TARDY;
        }
        return ATTENDANCE;
    }

    private static LocalTime getAttendanceReferenceTime(LocalDateTime dateTime) {
        if (DayOfWeek.MONDAY.equals(dateTime.getDayOfWeek())) {
            return MONDAY_ATTENDANCE_REFERENCE_TIME;
        }
        return NORMAL_ATTENDANCE_REFERENCE_TIME;
    }
}
