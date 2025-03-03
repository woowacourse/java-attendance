package domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANT, LATE, ABSENT;

    public static AttendanceStatus calculateAttendanceStatus(AttendanceRecord attendanceRecord) {
        final LocalTime time = attendanceRecord.getTime();
        if (time == null) {
            return ABSENT;
        }
        if (attendanceRecord.isSameDate(9) && time.isAfter(LocalTime.of(13, 30))) {
            return ABSENT;
        }
        if (attendanceRecord.isSameDate(9) && time.isAfter(LocalTime.of(13, 5))) {
            return LATE;
        }
        if (attendanceRecord.isSameDate(10) && time.isAfter(LocalTime.of(10, 30))) {
            return ABSENT;
        }
        if (attendanceRecord.isSameDate(10) && time.isAfter(LocalTime.of(10, 5))) {
            return LATE;
        }
        return ATTENDANT;
    }
}
