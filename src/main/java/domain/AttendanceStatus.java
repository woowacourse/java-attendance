package domain;

public enum AttendanceStatus {
    ATTENDANCE,
    LATE,
    ABSENCE;

    private static final int LATE_LIMIT_MINUTE = 5;
    private static final int ABSENCE_LIMIT_MINUTE = 30;

    public static AttendanceStatus findByStartHourAndAttendanceTime(int dayStartHour, AttendanceTime attendanceTime) {
        if (isAbsence(dayStartHour, attendanceTime)) {
            return ABSENCE;
        }

        if (isLate(dayStartHour, attendanceTime)) {
            return LATE;
        }

        if (isAttendance(dayStartHour, attendanceTime)) {
            return ATTENDANCE;
        }

        throw new IllegalArgumentException("[ERROR] 출석 상태를 판단하지 못했습니다");
    }

    private static boolean isAbsence(int dayStartHour, AttendanceTime attendanceTime) {
        return (attendanceTime.isEqualHour(dayStartHour) && attendanceTime.isAfterMinute(ABSENCE_LIMIT_MINUTE))
                || attendanceTime.isAfterHour(dayStartHour);
    }

    private static boolean isLate(int dayStartHour, AttendanceTime attendanceTime) {
        return attendanceTime.isEqualHour(dayStartHour) && attendanceTime.isAfterMinute(LATE_LIMIT_MINUTE);
    }

    private static boolean isAttendance(int dayStartHour, AttendanceTime attendanceTime) {
        return (attendanceTime.isEqualHour(dayStartHour) && attendanceTime.isEqualAndBeforeMinute(LATE_LIMIT_MINUTE))
                || attendanceTime.isBeforeHour(dayStartHour);
    }
}
