package model;

import java.util.EnumMap;

public enum PunishmentType {

    WARNING,
    MEETING,
    EXPULSION,
    NONE;

    public static PunishmentType find(EnumMap<AttendanceType, Integer> attendanceTotal) {
        Integer absenceTotal = attendanceTotal.get(AttendanceType.ABSENCE);
        if (absenceTotal > 5) {
            return EXPULSION;
        }
        if (absenceTotal >= 3) {
            return MEETING;
        }
        if (absenceTotal >= 2) {
            return WARNING;
        }
        return NONE;
    }
}
