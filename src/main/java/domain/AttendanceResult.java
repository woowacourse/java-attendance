package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceResult {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final int MONDAY_ATTENDANCE_HOUR = 13;
    private static final int OTHER_ATTENDANCE_HOUR = 10;
    private static final int LATE_MINUTES = 5;
    private static final int ABSENCE_MINUTES = 30;

    private final String result;

    AttendanceResult(String result) {
        this.result = result;
    }

    public static AttendanceResult findAttendanceResult(LocalDate attendanceDate, LocalTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (attendanceTime == null) {
            return ABSENCE;
        }
        return getAttendanceResult(attendanceTime, dayOfWeek);
    }

    private static AttendanceResult getAttendanceResult(LocalTime attendanceTime, DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return getMondayAttendanceResult(attendanceTime);
        }
        return getDefaultAttendanceResult(attendanceTime);
    }

    private static AttendanceResult getMondayAttendanceResult(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(MONDAY_ATTENDANCE_HOUR, ABSENCE_MINUTES))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(LocalTime.of(MONDAY_ATTENDANCE_HOUR, LATE_MINUTES))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static AttendanceResult getDefaultAttendanceResult(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(LocalTime.of(OTHER_ATTENDANCE_HOUR, ABSENCE_MINUTES))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(LocalTime.of(OTHER_ATTENDANCE_HOUR, LATE_MINUTES))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public String getResult() {
        return result;
    }
}