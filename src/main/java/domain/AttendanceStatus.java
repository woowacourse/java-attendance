package domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private final String koreanName;

    AttendanceStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    public static AttendanceStatus findByAttendanceTime(Week day, LocalTime attendanceTime) {
        if (attendanceTime.isBefore(day.getAttendanceTime())) {
            return ATTENDANCE;
        }
        if (attendanceTime.isBefore(day.getAttendanceTime().plusMinutes(30))) {
            return TARDINESS;
        }
        return ABSENCE;
    }
}
