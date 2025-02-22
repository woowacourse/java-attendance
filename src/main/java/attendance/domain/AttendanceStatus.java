package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    CHECKIN("출석"),
    ABSENCE("결석"),
    LATE("지각")
    ;

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus compute(LocalDateTime attendAt) {
        LocalTime time = attendAt.toLocalTime();

        if (attendAt.getDayOfWeek() == DayOfWeek.MONDAY) {
            return determineStatus(time, 13);
        }
        return determineStatus(time, 10);
    }

    private static AttendanceStatus determineStatus(LocalTime time, int hour) {
        if ((time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(hour, 5)))
            || time.equals(LocalTime.of(hour, 5))) {
            return AttendanceStatus.CHECKIN;
        }
        if (time.isAfter(LocalTime.of(hour, 5)) && time.isBefore(LocalTime.of(hour, 30))
            || time.equals(LocalTime.of(hour, 30))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENCE;
    }
    public String getName() {
        return name;
    }
}
