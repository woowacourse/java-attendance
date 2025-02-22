package domain;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public enum AttendanceStatus {
    
    출석,
    지각,
    결석,
    ;
    
    private static final int MAX_ATTEND_ADMIT_MINUTE = 5;
    private static final int MAX_LATE_ADMIT_MINUTE = 30;
    
    public static AttendanceStatus of(LocalTime targetAttendTime, LocalTime attendTime) {
        var minuteDifference = ChronoUnit.MINUTES.between(targetAttendTime, attendTime);
        
        if (minuteDifference <= MAX_ATTEND_ADMIT_MINUTE) return 출석;
        if (minuteDifference <= MAX_LATE_ADMIT_MINUTE) return 지각;
        return 결석;
    }
}
