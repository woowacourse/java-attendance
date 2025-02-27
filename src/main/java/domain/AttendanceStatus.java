package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("결석"),
    ABSENT("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus getStatusByAttendedTime(LocalDateTime attendedTime) {
        // 예외 로직 만들기
        int startTime = 10;
        if (attendedTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = 13;
        }
        // 시작 시각으로부터 30분 초과는 결석으로 간주한다.
        if ((attendedTime.getHour() == startTime && attendedTime.getMinute() > 30) || (attendedTime.getHour() > startTime)) {
            return ABSENT;
        }
        // 시작 시각으로부터 5분 초과는 지각으로 간주한다.
        if (attendedTime.getHour() == startTime && attendedTime.getMinute() > 5) {
            return LATE;
        }
        return ATTEND;
    }
}
