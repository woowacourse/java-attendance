package domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private final String displayName;

    private static final LocalTime startTime = LocalTime.of(8, 0);
    private static final LocalTime endTime = LocalTime.of(23, 0);

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public static AttendanceStatus findByAttendanceTime(Week day, LocalTime attendanceTime) {
        if (attendanceTime.isBefore(startTime) || attendanceTime.isAfter(endTime)) {
            return ABSENCE;
        }
        if (attendanceTime.isBefore(day.getAttendanceTime().plusMinutes(5)) || attendanceTime.equals(
                day.getAttendanceTime().plusMinutes(5))) {
            return ATTENDANCE;
        }
        if (attendanceTime.isBefore(day.getAttendanceTime().plusMinutes(30)) || attendanceTime.equals(
                day.getAttendanceTime().plusMinutes(30))) {
            return TARDINESS;
        }
        return ABSENCE;
    }

    public String getDisplayName() {
        return displayName;
    }
}
