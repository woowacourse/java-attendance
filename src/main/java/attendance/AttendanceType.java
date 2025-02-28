package attendance;

import static attendance.DayOfWeek.*;

import java.time.LocalDateTime;

public enum AttendanceType {
    ATTENDANCE(0), LATE(5), ABSENCE(30);

    private final int typeDecisionValue;

    AttendanceType(int typeDecisionValue) {
        this.typeDecisionValue = typeDecisionValue;
    }

    public static AttendanceType decideAttendanceType(LocalDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = findDayOfWeek(attendanceDateTime.toLocalDate());
        ifWeekendThrowException(dayOfWeek);
        ifNotOperatingThrowException(attendanceDateTime);
        return calculateAttendanceType(attendanceDateTime, dayOfWeek);
    }

    private static AttendanceType calculateAttendanceType(LocalDateTime attendanceDateTime,
        DayOfWeek dayOfWeek) {
        if (dayOfWeek.calculateTypeDecisionValueOnHour(attendanceDateTime) > 0) {
            return ABSENCE;
        }
        int decisionValue = dayOfWeek.calculateTypeDecisionValueOnMinute(attendanceDateTime);
        if (decisionValue >= ABSENCE.typeDecisionValue) {
            return ABSENCE;
        }
        if (decisionValue >= LATE.typeDecisionValue) {
            return LATE;
        }
        return ATTENDANCE;
    }

    private static void ifNotOperatingThrowException(LocalDateTime attendanceDateTime) {
        if (!OperatingTime.isOperating(attendanceDateTime.toLocalTime())) {
            throw new IllegalArgumentException("운영시간이 아닙니다.");
        }
    }

    private static void ifWeekendThrowException(DayOfWeek dayOfWeek) {
        if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
            throw new IllegalArgumentException("주말에는 등교할 수 없습니다.");
        }
    }
}
