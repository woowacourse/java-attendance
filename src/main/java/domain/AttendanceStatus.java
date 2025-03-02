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

    public static AttendanceStatus determineAttendanceStatus(LocalTime classStartTime, LocalTime checkInTime) {
        long minutesLate = Duration.between(classStartTime, checkInTime).toMinutes();
        if (minutesLate > ABSENCE.arrivalTimeLimit) {
            return ABSENCE;
        }
        if (minutesLate > LATE.arrivalTimeLimit) {
            return LATE;
        }
        return PRESENCE;
    }

    @Override
    public String toString() {
        if (this == PRESENCE) {
            return "출석";
        }
        if (this == LATE) {
            return "지각";
        }
        if (this == ABSENCE) {
            return "결석";
        }
        return super.toString();
    }
}
