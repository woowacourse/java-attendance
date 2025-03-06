package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANT, LATE, ABSENT;

    public static AttendanceStatus calculateAttendanceStatus(AttendanceRecord attendanceRecord) {
        final LocalTime time = attendanceRecord.getTime();
        final DayOfWeek dayOfWeek = attendanceRecord.getDate()
                .getDayOfWeek();
        if (attendanceRecord.isAbsence() || time.isAfter(AttendanceTimeInfo.getAbsenceTime(dayOfWeek))) {
            return ABSENT;
        }
        if (time.isAfter(AttendanceTimeInfo.getLateLocalTime(dayOfWeek))) {
            return LATE;
        }
        return ATTENDANT;
    }
}
