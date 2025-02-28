package domain;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public enum AttendanceType {

    SUCCESS(0), LATE(5), ABSENCE(30);

    private final int standardMinute;

    AttendanceType(int standardMinute) {
        this.standardMinute = standardMinute;
    }

    public static AttendanceType calculateType(AttendanceTime attendanceTime) {
        long difference = attendanceTime.calculateDifferenceFromAttendanceStandard();
        if (Duration.of(ABSENCE.standardMinute, ChronoUnit.MINUTES).toNanos() < difference) {
            return ABSENCE;
        }
        if (Duration.of(LATE.standardMinute, ChronoUnit.MINUTES).toNanos() < difference) {
            return LATE;
        }
        return SUCCESS;
    }
}
