package model;

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

    public static boolean checkHoliday(AttendanceDateTime attendanceDateTime) {
        return (attendanceDateTime.isWeekend() || attendanceDateTime.isChristmas());
    }

    public static HashMap<AttendanceStatus, Integer> recordAttendanceResult(List<AttendanceDateTime> record) {
        HashMap<AttendanceStatus, Integer> attendanceRecord = new HashMap<>();
        attendanceRecord.put(AttendanceStatus.ABSENT, 0);
        attendanceRecord.put(AttendanceStatus.LATE, 0);
        attendanceRecord.put(AttendanceStatus.ATTENDANCE, 0);
        for (AttendanceDateTime attendanceDateTime :record) {
            attendanceRecord.merge(calculateAttendance(attendanceDateTime, attendanceDateTime.toLocalTime()),
                     1, Integer::sum);
        }
        return attendanceRecord;
    }

    public static AttendanceStatus calculateAttendance(AttendanceDateTime attendanceDateTime, LocalTime localTime) {
        if (localTime.equals(UNREGISTERED_TIME)) {
            return AttendanceStatus.ABSENT;
        }
        if (attendanceDateTime.isMonday()) {
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
