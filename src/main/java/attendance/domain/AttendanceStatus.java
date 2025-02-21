package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    CHECKIN("출석"),
    ABSENCE("결석"),
    LATE("지각");

    private static final int CHECKIN_HOUR = 10;
    private static final int CHECKIN_HOUR_OF_MONDAY = 13;
    private static final int CHECKIN_MINUTE = 5;
    private static final int LATE_MINUTE = 30;
    private static final LocalTime OPERATING_TIME = LocalTime.of(8, 0);

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceStatus determineStatus(LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();

        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return determineStatusByDayOfWeek(attendanceTime, CHECKIN_HOUR_OF_MONDAY);
        }

        return determineStatusByDayOfWeek(attendanceTime, CHECKIN_HOUR);
    }

    private static AttendanceStatus determineStatusByDayOfWeek(LocalTime time, int hour) {
        LocalTime checkInLimitTime = LocalTime.of(hour, CHECKIN_MINUTE);
        LocalTime lateLimitTime = LocalTime.of(hour, LATE_MINUTE);

        if ((time.isAfter(OPERATING_TIME) && time.isBefore(checkInLimitTime)) || time.equals(checkInLimitTime)) {
            return CHECKIN;
        }
        if (time.isAfter(OPERATING_TIME) && time.isBefore(lateLimitTime) || time.equals(lateLimitTime)) {
            return LATE;
        }

        return ABSENCE;
    }
}
