package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public enum AttendanceCalculator {

    MONDAY(LocalTime.of(13, 5),LocalTime.of(13, 30)),
    TUESDAY_TO_FRIDAY(LocalTime.of(10, 5),LocalTime.of(10, 30));


    private final LocalTime lateTime;
    private final LocalTime absentTime;
    private static final int CHRISTMAS = 25;
    private static final LocalTime UNREGISTERED_TIME = LocalTime.of(0, 0);


    AttendanceCalculator(LocalTime lateTime, LocalTime absentTime) {
        this.lateTime = lateTime;
        this.absentTime = absentTime;
    }

    public static boolean checkHoliday(LocalDateTime localDateTime) {
        return (isWeekendDay(localDateTime) || isChristmas(localDateTime));
    }

    private static boolean isWeekendDay(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    private static boolean isChristmas(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfMonth() == CHRISTMAS);
    }

    public static HashMap<AttendanceStatus, Integer> recordAttendanceResult(List<LocalDateTime> record) {
        HashMap<AttendanceStatus, Integer> attendanceRecord = new HashMap<>();
        attendanceRecord.put(AttendanceStatus.ABSENT, 0);
        attendanceRecord.put(AttendanceStatus.LATE, 0);
        attendanceRecord.put(AttendanceStatus.ATTENDANCE, 0);
        for (LocalDateTime localDateTime :record) {
            attendanceRecord.merge(calculateAttendance(localDateTime, LocalTime.from(localDateTime)),
                     1, Integer::sum);
        }
        return attendanceRecord;
    }

    public static AttendanceStatus calculateAttendance(LocalDateTime localDateTime, LocalTime localTime) {
        if (localTime.equals(UNREGISTERED_TIME)) {
            return AttendanceStatus.ABSENT;
        }
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return getAttendanceStatus(localTime, AttendanceCalculator.MONDAY);
        }
        return getAttendanceStatus(localTime, AttendanceCalculator.TUESDAY_TO_FRIDAY);
    }

    private static AttendanceStatus getAttendanceStatus(LocalTime localTime,
                                                        AttendanceCalculator attendanceCalculatorByDay) {
        if (localTime.isBefore(attendanceCalculatorByDay.lateTime)) {
            return AttendanceStatus.ATTENDANCE;
        }
        if (localTime.isBefore(attendanceCalculatorByDay.absentTime)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }
}
