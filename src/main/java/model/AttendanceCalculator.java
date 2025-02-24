package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public enum AttendanceCalculator {

    MONDAY(LocalTime.of(13,5),LocalTime.of(13,30),1),
    TUESDAY(LocalTime.of(10,5),LocalTime.of(10,30),2),
    WEDNESDAY(LocalTime.of(10,5), LocalTime.of(10,30),3),
    THURSDAY(LocalTime.of(10,5), LocalTime.of(10,30),4),
    FRIDAY(LocalTime.of(10,5), LocalTime.of(10,30),5);

    private final LocalTime lateTime;
    private final LocalTime absentTime;
    private final int dayOfWeekValue;
    private static final int saturday = 6;
    private static final int sunday = 7;
    private static final int christmas = 25;

    AttendanceCalculator(LocalTime lateTime, LocalTime absentTime, int dayOfWeekValue) {
        this.lateTime = lateTime;
        this.absentTime = absentTime;
        this.dayOfWeekValue = dayOfWeekValue;
    }

    public static boolean checkHoliday(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfWeek().getValue() == saturday || localDateTime.getDayOfWeek().getValue() == sunday || localDateTime.getDayOfMonth() == christmas);
    }

    public static HashMap<String, Integer> recordAttendanceResult(List<LocalDateTime> record) {
        HashMap<String, Integer> attendanceRecord = new HashMap<>();
        attendanceRecord.put("결석", 0);
        attendanceRecord.put("출석", 0);
        attendanceRecord.put("지각", 0);
        for (LocalDateTime localDateTime :record) {
            int day = localDateTime.getDayOfWeek().getValue();
            attendanceRecord.put(calculateAttendance(day, LocalTime.from(localDateTime)).getState(),
                    attendanceRecord.get(calculateAttendance(day, LocalTime.from(localDateTime)).getState()) + 1);
        }
        return attendanceRecord;
    }

    public static AttendanceStatus calculateAttendance(int day, LocalTime localTime) {
        if (localTime.equals(LocalTime.of(0,0))) {
            return AttendanceStatus.ABSENT;
        }
        for (AttendanceCalculator attendanceCalculatorByDay : AttendanceCalculator.values()) {
            if (attendanceCalculatorByDay.dayOfWeekValue == day) {
                return getAttendanceStatus(localTime, attendanceCalculatorByDay);
            }
        }
        return null;
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
