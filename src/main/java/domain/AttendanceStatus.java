package domain;

import java.time.Duration;
import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENCE(0),
    LATE(5),
    ABSENCE(30),
    ;

    private final long arrivalTimeLimit;

    AttendanceStatus(long arrivalTimeLimit) {
        this.arrivalTimeLimit = arrivalTimeLimit;
    }

    public static AttendanceStatus getAttendanceStatus(LocalTime classStartTime, LocalTime checkInTime) {
        long timediff = Duration.between(classStartTime, checkInTime).toMinutes();
        System.out.println(timediff);
        if (timediff > ABSENCE.arrivalTimeLimit) {
            return ABSENCE;
        }
        if (timediff > LATE.arrivalTimeLimit) {
            return LATE;
        }
        return PRESENCE;
    }
}
