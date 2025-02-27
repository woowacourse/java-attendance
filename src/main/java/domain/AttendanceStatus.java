package domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    UNATTENDED("결석"),
    NO_SHOW("결석");

    private static final int LATE_MINUTE = 5;
    private static final int UNATTENDED_MINUTE = 30;

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus checkAttendanceStatus(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        LocalTime educationStartTime = attendanceDate.getEducationStartTime();
        LocalTime lateTime = educationStartTime.plusMinutes(LATE_MINUTE);
        LocalTime unattendedTime = educationStartTime.plusMinutes(UNATTENDED_MINUTE);

        return determineAttendanceStatus(attendanceTime, lateTime, unattendedTime);
    }

    private static AttendanceStatus determineAttendanceStatus(AttendanceTime attendanceTime,
                                                              LocalTime lateTime,
                                                              LocalTime unattendedTime) {
        if (attendanceTime.isAfter(lateTime) && attendanceTime.isBefore(unattendedTime.plusMinutes(1))) {
            return LATE;
        }
        if (attendanceTime.isAfter(unattendedTime)) {
            return UNATTENDED;
        }
        return ATTEND;
    }

    public String getStatus() {
        return status;
    }
}
