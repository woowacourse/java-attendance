package domain;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    UNATTENDED("결석"),
    NO_SHOW("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus checkAttendanceStatus(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        if (attendanceTime.isAfterLateTime(attendanceDate)) {
            return LATE;
        }
        if (attendanceTime.isAfterAbsentTime(attendanceDate)) {
            return UNATTENDED;
        }
        return ATTEND;
    }

    public String getStatus() {
        return status;
    }
}
