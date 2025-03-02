package attendance.domain;

import static attendance.domain.DayOfWeek.*;

public enum AttendanceType {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENCE("결석", 30);

    private final String type;
    private final int typeDecisionValue;

    AttendanceType(String type, int typeDecisionValue) {
        this.type = type;
        this.typeDecisionValue = typeDecisionValue;
    }

    public static AttendanceType decideAttendanceType(AttendanceTime attendanceTime) {
        DayOfWeek dayOfWeek = findDayOfWeek(attendanceTime.getDate());
        return determineBy(attendanceTime, dayOfWeek);
    }

    public String getType() {
        return type;
    }

    private static AttendanceType determineBy(AttendanceTime attendanceTime,
        DayOfWeek dayOfWeek) {
        if (attendanceTime.isAbsenceDate()) {
            return ABSENCE;
        }
        if (dayOfWeek.calculateTypeDecisionValueOnHour(attendanceTime) > 0) {
            return ABSENCE;
        }
        int decisionValue = dayOfWeek.calculateTypeDecisionValueOnMinute(attendanceTime);
        if (decisionValue >= ABSENCE.typeDecisionValue) {
            return ABSENCE;
        }
        if (decisionValue >= LATE.typeDecisionValue) {
            return LATE;
        }
        return ATTENDANCE;
    }
}
