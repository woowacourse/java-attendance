package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public enum AttendanceRuleByDay {

    MONDAY(LocalTime.of(13, 0), DayOfWeek.MONDAY, "월요일"),
    TUESDAY(LocalTime.of(10, 0), DayOfWeek.TUESDAY, "화요일"),
    WEDNESDAY(LocalTime.of(10, 0), DayOfWeek.WEDNESDAY, "수요일"),
    THURSDAY(LocalTime.of(10, 0), DayOfWeek.THURSDAY, "목요일"),
    FRIDAY(LocalTime.of(10, 0), DayOfWeek.FRIDAY, "금요일");

    private final LocalTime classStartTime;
    private final DayOfWeek dayOfWeek;
    private final String day;

    AttendanceRuleByDay(LocalTime classStartTime, DayOfWeek dayOfWeek, String day) {
        this.classStartTime = classStartTime;
        this.dayOfWeek = dayOfWeek;
        this.day = day;
    }

    public static AttendanceStatus calculateAttendance(LocalDateTime localDateTime) {
        DayOfWeek day = localDateTime.getDayOfWeek();
        LocalTime arrivalTime = LocalTime.from(localDateTime);
        return Arrays.stream(values())
                .filter(attendanceRule -> attendanceRule.dayOfWeek == day)
                .map(attendanceRule -> attendanceRule.calculateAttendanceStatusByArrivalTime(arrivalTime))
                .findFirst()
                .orElseThrow();
    }

    public static String findDayByDayOfWeekValue(DayOfWeek dayOfWeekValue) {
        return Arrays.stream(values())
                .filter(attendanceRule -> attendanceRule.dayOfWeek == dayOfWeekValue)
                .map(attendanceRule -> attendanceRule.day)
                .findFirst()
                .orElseThrow();
    }

    private AttendanceStatus calculateAttendanceStatusByArrivalTime(LocalTime arrivalTime) {
        int late = (int) classStartTime.until(arrivalTime, ChronoUnit.MINUTES);
        return AttendanceStatus.fromMinutesLate(late);
    }

}
