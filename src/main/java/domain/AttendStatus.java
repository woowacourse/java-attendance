package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendStatus {
    ATTEND, LATE, ABSENCE;

    public static AttendStatus calculateAttend(Attend attend) {
        final LocalTime targetTime = attend.time;
        final DayOfWeek dayOfWeek = attend.getDayOfWeek();
        if (targetTime == null || targetTime.isAfter(AttendanceTimeInfo.getAbsenceTime(dayOfWeek))) {
            return ABSENCE;
        }
        if (targetTime.isAfter(AttendanceTimeInfo.getLateLocalTime(dayOfWeek))) {
            return LATE;
        }
        return ATTEND;
    }
}
