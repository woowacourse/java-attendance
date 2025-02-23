package model;

import java.time.LocalDateTime;
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
        if (AttendanceTime.isLate(localDateTime, BE_LATE.standardMinute, ABSENCE.standardMinute)) {
            return BE_LATE;
        }
        if (AttendanceTime.isAbsence(localDateTime, ABSENCE.standardMinute)) {
            return ABSENCE;
        }
        return SUCCESS;
    }

    public static int calculateConvertedAbsenceCount(Map<AttendanceType, Integer> counts) {
        return counts.getOrDefault(BE_LATE, 0) / 3 + counts.getOrDefault(ABSENCE, 0);
    }
}
