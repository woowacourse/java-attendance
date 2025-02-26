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
        int attendanceHour = calculateAttendanceHour(attendance.getDayOfWeek());
        LocalTime lateCondition = LocalTime.of(attendanceHour, START_MINUTE_OF_LATE);
        if(attendance.getTime().isBefore(lateCondition)) {
            return AttendanceStatus.ATTEND;
        }
        return null;
    }

    private static int calculateAttendanceHour(DayOfWeek dayOfWeek) {
        if(dayOfWeek == DayOfWeek.MONDAY) {
            return ATTENDANCE_HOUR_OF_MONDAY;
        }
        return ATTENDANCE_HOUR_OF_TUESDAY_TO_FRIDAY;
    }
}
