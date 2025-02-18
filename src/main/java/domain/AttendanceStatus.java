package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    TARDY("지각"),
    ABSENCE("결석");

    public String getStatus() {
        return status;
    }

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus attend(LocalDateTime target) {
        DayOfWeek dayOfWeek = target.getDayOfWeek();

        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalTime targetTime = target.toLocalTime();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException();
        }

        if (dayOfWeek == DayOfWeek.MONDAY) {
            attendanceTime = LocalTime.of(13, 0);
        }


        if (targetTime.isAfter(attendanceTime)) {
            if (attendanceTime.plusMinutes(30).isBefore(targetTime)) {
                return ABSENCE;
            }
            if (attendanceTime.plusMinutes(5).isBefore(targetTime)) {
                return TARDY;
            }
            return ATTENDANCE;
        }
        return ATTENDANCE;
    }
}
