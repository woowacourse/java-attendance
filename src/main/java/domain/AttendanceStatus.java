package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("결석"),
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
        // 시작 시각으로부터 30분 초과는 결석으로 간주한다.
        if ((attendedTime.getHour() == startTime && attendedTime.getMinute() > 30) || (attendedTime.getHour()
                > startTime)) {
            return ABSENT;
        }
        // 시작 시각으로부터 5분 초과는 지각으로 간주한다.
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
}
