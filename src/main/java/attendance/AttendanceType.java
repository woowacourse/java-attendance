package attendance;

import static attendance.DayOfWeek.*;

public enum AttendanceType {
    ATTENDANCE(0), LATE(5), ABSENCE(30);

    private final int typeDecisionValue;

    AttendanceType(int typeDecisionValue) {
        this.typeDecisionValue = typeDecisionValue;
    }

    public static AttendanceType decideAttendanceType(AttendanceTime attendanceTime) {
        DayOfWeek dayOfWeek = findDayOfWeek(attendanceTime.getDate());
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
