package model;

import java.time.LocalDateTime;

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
}
