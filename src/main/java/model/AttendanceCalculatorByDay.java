package model;

import java.time.LocalTime;

public enum AttendanceCalculatorByDay {

    MONDAY(LocalTime.of(13,5),LocalTime.of(13,30),1,"월요일"),
    TUESDAY(LocalTime.of(10,5),LocalTime.of(10,30),2,"화요일"),
    WEDNESDAY(LocalTime.of(10,5), LocalTime.of(10,30),3,"수요일"),
    THURSDAY(LocalTime.of(10,5), LocalTime.of(10,30),4,"목요일"),
    FRIDAY(LocalTime.of(10,5), LocalTime.of(10,30),5,"금요일");

    private final LocalTime lateTime;
    private final LocalTime absentTime;
    private final int dayOfWeekValue;
    private final String day;

    AttendanceCalculatorByDay(LocalTime lateTime, LocalTime absentTime, int dayOfWeekValue, String day) {
        this.lateTime = lateTime;
        this.absentTime = absentTime;
        this.dayOfWeekValue = dayOfWeekValue;
        this.day = day;
    }

    public static AttendanceStatus attendanceCalculator(int day, LocalTime localTime) {
        if (localTime.equals(LocalTime.of(0,0))) {
            return AttendanceStatus.ABSENT;
        }
        for (AttendanceCalculatorByDay attendanceCalculatorByDay : AttendanceCalculatorByDay.values()) {
            if (attendanceCalculatorByDay.dayOfWeekValue == day) {
                return getAttendanceStatus(localTime, attendanceCalculatorByDay);
            }
        }
        return null;
    }

    private static AttendanceStatus getAttendanceStatus(LocalTime localTime,
                                                        AttendanceCalculatorByDay attendanceCalculatorByDay) {
        if (localTime.isBefore(attendanceCalculatorByDay.lateTime)) {
            return AttendanceStatus.ATTENDANCE;
        }
        if (localTime.isBefore(attendanceCalculatorByDay.absentTime)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }


    public static String findDayByDayOfWeekValue(int dayOfWeekValue) {
        for (AttendanceCalculatorByDay attendanceCalculatorByDay : AttendanceCalculatorByDay.values()) {
            if (attendanceCalculatorByDay.dayOfWeekValue == dayOfWeekValue) {
                return attendanceCalculatorByDay.day;
            }
        }
        return null;
    }
}
