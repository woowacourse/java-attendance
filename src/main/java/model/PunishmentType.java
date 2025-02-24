package model;

import java.util.Map;

public enum PunishmentType {

    WARNING,
    MEETING,
    EXPULSION,
    NONE;

    public static PunishmentType calculateType(Map<AttendanceType, Integer> counts) {
        int absenceCount = counts.get(AttendanceType.ABSENCE) + counts.get(AttendanceType.BE_LATE) / 3;
        if (absenceCount > 5) {
            return EXPULSION;
        }
        if (absenceCount >= 3) {
            return MEETING;
        }
        if (absenceCount >= 2) {
            return WARNING;
        }
        return NONE;
    }
}
