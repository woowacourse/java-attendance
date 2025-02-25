package domain;

import static domain.AttendanceStandard.ABSENT_DEADLINE;
import static domain.AttendanceStandard.CLOSE_TIME;
import static domain.AttendanceStandard.LATE_DEADLINE;
import static domain.AttendanceStandard.MONDAY_START_HOUR;
import static domain.AttendanceStandard.NON_MONDAY_START_HOUR;
import static domain.AttendanceStandard.OPEN_TIME;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTENDED("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus checkAttendanceState(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        validateRunningTime(localDateTime, dayOfWeek, hour);
        int startHour = NON_MONDAY_START_HOUR.getTime();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            startHour = MONDAY_START_HOUR.getTime();
        }
        return decideAttendanceState(startHour, hour, minute);
    }

    private static AttendanceStatus decideAttendanceState(int startHour, int hour, int minute) {
        if (hour < startHour || (hour == startHour && minute < LATE_DEADLINE.getTime())) {
            return ATTENDED;
        }
        if ((hour == startHour) && minute <= ABSENT_DEADLINE.getTime()) {
            return LATE;
        }
        return ABSENT;
    }

    private static void validateRunningTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek, int hour) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || localDateTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("주말 또는 공휴일은 캠퍼스 휴장");
        }
        if (hour < OPEN_TIME.getTime() || hour == CLOSE_TIME.getTime()) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아님");
        }
    }

    public String getStringValue() {
        return status;
    }
}
