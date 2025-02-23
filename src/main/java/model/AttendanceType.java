package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public enum AttendanceType {

    SUCCESS(0),
    BE_LATE(5),
    ABSENCE(30),
    ;

    private final int standardMinute;

    AttendanceType(int standardMinute) {
        this.standardMinute = standardMinute;
    }

    public static AttendanceType calculateType(LocalDateTime localDateTime) {
        long millisDifference = AttendanceTime.calculateMillisDifferenceFromStartTime(localDateTime);
        if (millisDifference > Duration.of(ABSENCE.standardMinute, ChronoUnit.MINUTES).toMillis()) {
            return ABSENCE;
        }
        if (millisDifference > Duration.of(BE_LATE.standardMinute, ChronoUnit.MINUTES).toMillis()) {
            return BE_LATE;
        }
        return SUCCESS;
    }

    public static int calculateConvertedAbsenceCount(Map<AttendanceType, Integer> counts) {
        return counts.getOrDefault(BE_LATE, 0) / 3 + counts.getOrDefault(ABSENCE, 0);
    }
}
