package model;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceType {

    SUCCESS(0),
    BE_LATE(5),
    ABSENCE(30),
    ;

    private final int standardMinute;

    AttendanceType(int standardMinute) {
        this.standardMinute = standardMinute;
    }

    public static AttendanceType calculate(LocalDate localDate, LocalTime localTime) {
        if (AttendanceTime.isLate(localDate, localTime, BE_LATE.standardMinute, ABSENCE.standardMinute)) {
            return BE_LATE;
        }
        if (AttendanceTime.isAbsence(localDate, localTime, ABSENCE.standardMinute)) {
            return ABSENCE;
        }
        return SUCCESS;
    }
}
