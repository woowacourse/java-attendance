package domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND,
    LATE,
    UNATTENDED,
    NO_SHOW;

    private static final int LATE_MINUTE = 5;
    private static final int UNATTENDED_MINUTE = 30;

    public static AttendanceStatus checkAttendanceStatus(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        LocalTime educationStartTime = attendanceDate.getEducationStartTime();
        LocalTime lateTime = educationStartTime.plusMinutes(LATE_MINUTE);
        LocalTime unattendedTime = educationStartTime.plusMinutes(UNATTENDED_MINUTE);

        if (attendanceTime.isAfter(lateTime)
                && attendanceTime.isBefore(unattendedTime.plusMinutes(1))) {
            return LATE;
        }
        if (attendanceTime.isAfter(unattendedTime)) {
            return UNATTENDED;
        }
        return ATTEND;
    }
}
