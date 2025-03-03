package domain.attendance;

public enum AttendanceStatus {

    ATTEND,
    LATE,
    ABSENCE,
    ;

    public static AttendanceStatus from(AttendanceTime attendanceTime) {
        if (attendanceTime.isAbsence()) {
            return ABSENCE;
        }
        if (attendanceTime.isLate()) {
            return LATE;
        }
        return ATTEND;
    }
}
