package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    
    출석,
    지각,
    결석,
    ;
    
    private static final LocalTime MONDAY_LATE_THRESHOLD = LocalTime.of(13, 30);
    private static final LocalTime MONDAY_ATTEND_THRESHOLD = LocalTime.of(13, 5);
    private static final LocalTime NOT_MONDAY_LATE_THRESHOLD = LocalTime.of(10, 30);
    private static final LocalTime NOT_MONDAY_ATTEND_THRESHOLD = LocalTime.of(10, 5);
    
    public static AttendanceStatus of(DayOfWeek dayOfWeek, LocalTime time) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return calculateAttendanceStatusOf(time, MONDAY_LATE_THRESHOLD, MONDAY_ATTEND_THRESHOLD);
        }
        
        return calculateAttendanceStatusOf(time, NOT_MONDAY_LATE_THRESHOLD, NOT_MONDAY_ATTEND_THRESHOLD);
    }
    
    private static AttendanceStatus calculateAttendanceStatusOf(final LocalTime time, final LocalTime lateThreshold, final LocalTime attendThreshold) {
        if (time.isAfter(lateThreshold)) {
            return AttendanceStatus.결석;
        }
        if (time.isAfter(attendThreshold)) {
            return AttendanceStatus.지각;
        }
        return AttendanceStatus.출석;
    }
}
