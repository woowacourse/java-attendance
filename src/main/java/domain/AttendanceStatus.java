package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus getStatusByAttendedTime(LocalDateTime attendedTime) {
        if (isWeekendOrChristmas(attendedTime.toLocalDate()))
            throw new IllegalArgumentException(ERROR_MESSAGE.CLOSED_DAY.getMessage());

        if (isNotOpenTime(attendedTime))
            throw new IllegalArgumentException(ERROR_MESSAGE.CLOSED_TIME.getMessage());

        int startTime = 10;
        if (attendedTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = 13;
        }
        if ((attendedTime.getHour() == startTime && attendedTime.getMinute() > 30) || (attendedTime.getHour()
                > startTime)) {
            return ABSENT;
        }
        if (attendedTime.getHour() == startTime && attendedTime.getMinute() > 5) {
            return LATE;
        }
        return ATTEND;
    }

    private static boolean isNotOpenTime(LocalDateTime attendedTime) {
        return attendedTime.getHour() < 8 || attendedTime.getHour() == 23;
    }

    static boolean isWeekendOrChristmas(LocalDate attendedDay) {
        return (attendedDay.getDayOfWeek() == DayOfWeek.SATURDAY)
                || (attendedDay.getDayOfWeek() == DayOfWeek.SUNDAY)
                || attendedDay.getDayOfMonth() == 25;
    }

    public String getValue() {
        return status;
    }
}
