package model;

import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceRuleByDay {

    MONDAY(LocalTime.of(13,0),1,"월요일"),
    TUESDAY(LocalTime.of(10,0),2,"화요일"),
    WEDNESDAY(LocalTime.of(10,0),3,"수요일"),
    THURSDAY(LocalTime.of(10,0),4,"목요일"),
    FRIDAY(LocalTime.of(10,0),5,"금요일");

    private final LocalTime classStartTime;
    private final int dayOfWeekValue;
    private final String day;

    AttendanceRuleByDay(LocalTime classStartTime, int dayOfWeekValue, String day) {
        this.classStartTime = classStartTime;
        this.dayOfWeekValue = dayOfWeekValue;
        this.day = day;
    }
    public static AttendanceStatus calculateAttendance(int day, LocalTime arrivalTime) {
        if (arrivalTime.equals(LocalTime.of(0, 0))) {
            return AttendanceStatus.ABSENT;
        }
        return Arrays.stream(values())
                .filter(d -> d.dayOfWeekValue == day)
                .map(d -> d.getAttendanceStatus(arrivalTime))
                .findFirst()
                .orElseThrow();
    }

    private AttendanceStatus getAttendanceStatus(LocalTime arrivalTime) {
        if (arrivalTime.isBefore(classStartTime.plusMinutes(5))) {
            return AttendanceStatus.ATTENDANCE;
        }
        if (arrivalTime.isBefore(classStartTime.plusMinutes(30))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }


    public static String findDayByDayOfWeekValue(int dayOfWeekValue) {
        return Arrays.stream(values())
                .filter(d -> d.dayOfWeekValue == dayOfWeekValue)
                .map(d -> d.day)
                .findFirst()
                .orElseThrow();
    }
}
