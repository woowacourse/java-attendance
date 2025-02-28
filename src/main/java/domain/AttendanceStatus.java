package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String expression;
    private static final int ATTENDANCE_HOUR_OF_MONDAY = 13;
    private static final int ATTENDANCE_HOUR_OF_TUESDAY_TO_FRIDAY = 10;
    private static final int END_MINUTE_OF_ATTENDANCE = 5;
    private static final int END_MINUTE_OF_LATE = 30;

    AttendanceStatus(String expression) {
        this.expression = expression;
    }

    public static AttendanceStatus from(Attendance attendance) {
        int endHourOfAttendance = calculateEndHourOfAttendance(attendance.getDayOfWeek());
        LocalTime checkTime = attendance.getTime();
        if(checkTime.isAfter(LocalTime.of(endHourOfAttendance, END_MINUTE_OF_LATE))) {
            return AttendanceStatus.ABSENT;
        }
        if(checkTime.isAfter(LocalTime.of(endHourOfAttendance, END_MINUTE_OF_ATTENDANCE))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTEND;
    }

    private static int calculateEndHourOfAttendance(DayOfWeek dayOfWeek) {
        if(dayOfWeek == DayOfWeek.MONDAY) {
            return ATTENDANCE_HOUR_OF_MONDAY;
        }
        return ATTENDANCE_HOUR_OF_TUESDAY_TO_FRIDAY;
    }

    public String getExpression() {
        return expression;
    }

    public static boolean isAbsentStatus(AttendanceStatus attendanceStatus) {
        return attendanceStatus == ABSENT;
    }
}
