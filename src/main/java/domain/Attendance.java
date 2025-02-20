package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {
    private final LocalDateTime dateAndTime;
    private final String state;

    public Attendance(LocalDateTime localDateTime) {
        dateAndTime = localDateTime;
        state = checkAttendanceState(localDateTime);
    }

    public int getDayOfMonth() {
        return dateAndTime.getDayOfMonth();
    }

    public String getState() {
        return state;
    }

    public String getFormattedTimeAndState() {
        if (this.state.equals("결석")) {
            return "--:-- " + "(" + this.state + ")";
        }
        return dateAndTime.format(DateTimeFormatter.ofPattern("HH:mm ", Locale.KOREAN)) + "(" + this.state + ")";
    }

    public String getFormattedAttended() {
        return dateAndTime.format(DateTimeFormatter.ofPattern("MM월 dd일 "))
                + dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + " "
                + getFormattedTimeAndState();
    }

    private String checkAttendanceState(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        validateRunningTime(localDateTime, dayOfWeek, hour);
        int startHour = 10;
        if (dayOfWeek == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        return decideAttendanceState(startHour, hour, minute);
    }

    private String decideAttendanceState(int startHour, int hour, int minute) {
        if (hour < startHour || (hour == startHour && minute < 5)) {
            return "출석";
        }
        if ((hour == startHour) && minute <= 30) {
            return "지각";
        }
        return "결석";
    }

    private void validateRunningTime(LocalDateTime localDateTime, DayOfWeek dayOfWeek, int hour) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || localDateTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("주말 또는 공휴일은 캠퍼스 휴장");
        }
        if (hour < 8 || hour == 23) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아님");
        }
    }

    public boolean isEqualDate(LocalDateTime localDateTime) {
        return dateAndTime.toLocalDate().isEqual(localDateTime.toLocalDate());
    }
}
