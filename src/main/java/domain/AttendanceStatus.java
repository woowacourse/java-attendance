package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석"),
    TRUANCY("결석"),
    ;

    private final String expression;

    AttendanceStatus(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public static AttendanceStatus of(LocalDateTime inputTime) {
        DayOfWeek dayOfWeek = inputTime.getDayOfWeek();
        int startHour = 10;
        if (dayOfWeek == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        if (inputTime.toLocalTime().toNanoOfDay() <= LocalTime.of(startHour, 5, 0).toNanoOfDay()) {
            return ATTENDANCE;
        }
        if (inputTime.toLocalTime().toNanoOfDay() <= LocalTime.of(startHour, 30, 0).toNanoOfDay()) {
            return LATE;
        }
        return ABSENCE;
    }
}
