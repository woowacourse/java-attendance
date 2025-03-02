package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND,
    TARDY,
    ABSENCE;

    private static final AttendanceTime MONDAY_START_TIME = new AttendanceTime(LocalTime.of(13, 0));
    private static final AttendanceTime START_TIME = new AttendanceTime(LocalTime.of(10, 0));


    private static AttendanceTime startTime(AttendanceDate date) {
        if (date.getDate().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return MONDAY_START_TIME;
        }
        return START_TIME;
    }

    public static AttendanceStatus getAttendanceStatus(AttendanceDate date, AttendanceTime attendanceTime) {
        if (isAbsence(date, attendanceTime)) {
            return ABSENCE;
        }
        if (isTardy(date, attendanceTime)) {
            return TARDY;
        }
        return ATTEND;
    }

    private static boolean isAbsence(AttendanceDate date, AttendanceTime attendanceTime) {
        return attendanceTime.getTime().isAfter(startTime(date).getTime().plusMinutes(30));
    }

    private static boolean isTardy(AttendanceDate date, AttendanceTime attendanceTime) {
        return attendanceTime.getTime().isAfter(startTime(date).getTime().plusMinutes(5)) &&
                !attendanceTime.getTime().isAfter(startTime(date).getTime().plusMinutes(30));
    }
}
