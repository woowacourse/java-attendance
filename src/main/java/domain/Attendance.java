package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Attendance {
    private final String state;

    public Attendance(LocalDateTime localDateTime) {
        state = checkAttendanceState(localDateTime);
    }

    private String checkAttendanceState(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || localDateTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("주말 또는 공휴일은 캠퍼스 휴장");
        }

        if(hour < 8 || hour == 23) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아님");
        }

        int startHour = 10;

        if (dayOfWeek == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        if(hour < startHour){
            return "출석";
        }
        if (hour == startHour) {
            if (minute <= 5) {
                return "출석";
            }
            if (minute <= 30) {
                return "지각";
            }
        }
        return "결석";
    }

    public String getState() {
        return state;
    }
}
