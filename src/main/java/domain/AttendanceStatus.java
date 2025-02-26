package domain;

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
        DayOfWeek dayOfWeek = attendance.getDayOfWeek();
        int startHour = calculateStartHour(dayOfWeek);
        LocalTime lateCondition = LocalTime.of(startHour, 6);
        if(attendance.getTime().isBefore(lateCondition)) {
            return AttendanceStatus.ATTEND;
        }
        return null;
    }

    private static int calculateStartHour(DayOfWeek dayOfWeek) {
        if(dayOfWeek == DayOfWeek.MONDAY) {
            return 13;
        }
        return 10;
    }
}
