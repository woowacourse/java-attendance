package domain;

import constant.AttendanceStandard;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static constant.AttendanceStandard.*;
import static constant.AttendanceStatus.*;

public class Attendance {
    private final LocalDateTime dateAndTime;
    private final String state;

    public Attendance(LocalDateTime localDateTime) {
        dateAndTime = localDateTime;
        state = checkAttendanceState(localDateTime);
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public int getDayOfMonth() {
        return dateAndTime.getDayOfMonth();
    }

    public String getState() {
        return state;
    }



    private String checkAttendanceState(LocalDateTime localDateTime) {
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

    private String decideAttendanceState(int startHour, int hour, int minute) {
        if (hour < startHour || (hour == startHour && minute < LATE_DEADLINE.getTime())) {
            return ATTENDED.getStatus();
        }
        if ((hour == startHour) && minute <= ABSENT_DEADLINE.getTime()) {
            return LATE.getStatus();
        }
        return ABSENT.getStatus();
    }

    private void validateRunningTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek, int hour) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || localDateTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("주말 또는 공휴일은 캠퍼스 휴장");
        }
        if (hour < OPEN_TIME.getTime() || hour == CLOSE_TIME.getTime()) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아님");
        }
    }

    public boolean isEqualDate(LocalDateTime localDateTime) {
        return dateAndTime.toLocalDate().isEqual(localDateTime.toLocalDate());
    }
}
