package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static constant.ErrorMessage.CLOSED_DAY;
import static constant.ErrorMessage.CLOSED_TIME;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각", 5),
    ABSENT("결석", 30);

    private String status;
    private int timeLimit;

    private static final int OPEN_TIME = 8;
    private static final int CLOSE_TIME = 23;
    private static final int CHRISTMAS = 25;

    AttendanceStatus(String status) {
        this.status = status;
    }

    AttendanceStatus(String status, int timeLimit) {
        this.status = status;
        this.timeLimit = timeLimit;
    }

    public static AttendanceStatus getStatusByAttendedTime(LocalDateTime attendedTime) {
        validateCampusIsOpen(attendedTime);
        int startTime = 10;
        if (attendedTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = 13;
        }
        if ((attendedTime.getHour() == startTime && attendedTime.getMinute() > ABSENT.timeLimit) || (attendedTime.getHour() > startTime)) {
            return ABSENT;
        }
        if (attendedTime.getHour() == startTime && attendedTime.getMinute() > LATE.timeLimit) {
            return LATE;
        }
        return ATTEND;
    }

    public static void validateCampusIsOpen(LocalDateTime attendedTime) {
        if (isWeekendOrChristmas(attendedTime.toLocalDate()))
            throw new IllegalArgumentException(CLOSED_DAY.getMessage());
        if (isNotOpenTime(attendedTime))
            throw new IllegalArgumentException(CLOSED_TIME.getMessage());
    }

    private static boolean isNotOpenTime(LocalDateTime attendedTime) {
        return attendedTime.getHour() < OPEN_TIME || attendedTime.getHour() == CLOSE_TIME;
    }

    static boolean isWeekendOrChristmas(LocalDate attendedDay) {
        return (attendedDay.getDayOfWeek() == SATURDAY)
                || (attendedDay.getDayOfWeek() == SUNDAY)
                || attendedDay.getDayOfMonth() == CHRISTMAS;
    }

    public String getValue() {
        return status;
    }
}
