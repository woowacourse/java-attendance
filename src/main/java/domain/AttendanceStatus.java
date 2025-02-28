package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND,
    TARDY,
    ABSENCE;

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime START_TIME = LocalTime.of(10, 0);
    public static LocalTime DEFAULT_TIME = LocalTime.of(0, 0);


    private static LocalTime startTime(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return MONDAY_START_TIME;
        }
        return START_TIME;
    }

    public static AttendanceStatus getAttendanceStatus(LocalDate date, LocalTime attendanceTime) {
        if(isAbsence(date, attendanceTime)) {
            return ABSENCE;
        }
        if(isTardy(date, attendanceTime)) {
            return TARDY;
        }
        return ATTEND;
    }

    private static boolean isAbsence(LocalDate date, LocalTime attendanceTime) {
        return attendanceTime.isAfter(startTime(date).plusMinutes(30));
    }

    private static boolean isTardy(LocalDate date, LocalTime attendanceTime) {
        return attendanceTime.isAfter(startTime(date).plusMinutes(5)) &&
                        !attendanceTime.isAfter(startTime(date).plusMinutes(30));
    }
}
