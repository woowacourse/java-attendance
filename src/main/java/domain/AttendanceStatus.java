package domain;

import static util.Constants.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String expression;

    AttendanceStatus(String expression) {
        this.expression = expression;
    }

    public static AttendanceStatus from(Attendance attendance) {
        int endHourOfAttendance = calculateEndHourOfAttendance(attendance.getDayOfWeek());
        LocalTime endTimeOfAttendance = LocalTime.of(endHourOfAttendance, END_MINUTE_OF_ATTENDANCE);
        LocalTime checkTime = attendance.getTime();

        if(checkTime.isBefore(endTimeOfAttendance) || checkTime.equals(endTimeOfAttendance)) {
            return AttendanceStatus.ATTEND;
        }
        if(checkTime.isAfter(endTimeOfAttendance)) {
            return AttendanceStatus.LATE;
        }
        return null;
    }

    private static int calculateEndHourOfAttendance(DayOfWeek dayOfWeek) {
        if(dayOfWeek == DayOfWeek.MONDAY) {
            return ATTENDANCE_HOUR_OF_MONDAY;
        }
        return ATTENDANCE_HOUR_OF_TUESDAY_TO_FRIDAY;
    }
}
