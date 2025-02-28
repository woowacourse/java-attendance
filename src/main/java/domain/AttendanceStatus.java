package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus judge(LocalDateTime localDateTime) {
        LocalTime attendanceTime = localDateTime.toLocalTime();
        StandardTime standardTime = StandardTime.findByDayOfWeek(localDateTime.toLocalDate().getDayOfWeek());

        if (attendanceTime.isAfter(standardTime.getAbsentTime())) return AttendanceStatus.ABSENCE;

        if (attendanceTime.isAfter(standardTime.getLateTime())) return AttendanceStatus.LATENESS;

        return AttendanceStatus.ATTENDANCE;
    }

    public String getName() {
        return name;
    }
}
