package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String description;

    AttendanceStatus(String description) {
        this.description = description;
    }

    public static AttendanceStatus findStatus(LocalDateTime attendDateTime) {
        int openHour = findOpenHour(attendDateTime);
        LocalTime attendTime = attendDateTime.toLocalTime();
        if (attendTime.isBefore(LocalTime.of(openHour, 5))) {
            return ATTEND;
        } else if (attendTime.isBefore(LocalTime.of(openHour, 30))) {
            return LATE;
        }
        return ABSENT;
    }

    private static int findOpenHour(LocalDateTime attendDateTime) {
        if (attendDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return 13;
        }
        return 10;
    }

    public String getDescription() {
        return description;
    }
}
