package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final static DayOfWeek ATTENDANCE_START_1PM_DAY = DayOfWeek.MONDAY;
    private final static List<DayOfWeek> ATTENDANCE_START_10AM_DAYS = List.of(
            DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    private static final LocalTime ATTENDANCE_CUTOFF_1PM = LocalTime.of(13, 5);
    private static final LocalTime LATENESS_CUTOFF_1PM = LocalTime.of(13, 30);
    private static final LocalTime ATTENDANCE_CUTOFF_10AM = LocalTime.of(10, 5);
    private static final LocalTime LATENESS_CUTOFF_10AM = LocalTime.of(10, 30);

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus from(LocalDate date, LocalTime time) {
        return determineStatus(date, time);
    }

    public static AttendanceStatus determineStatus(LocalDate date, LocalTime time) {
        if (date.getDayOfWeek() == ATTENDANCE_START_1PM_DAY) {
            return decideByTime(time, ATTENDANCE_CUTOFF_1PM, LATENESS_CUTOFF_1PM);
        }

        if (ATTENDANCE_START_10AM_DAYS.contains(date.getDayOfWeek())) {
            return decideByTime(time, ATTENDANCE_CUTOFF_10AM, LATENESS_CUTOFF_10AM);
        }

        return null;
    }

    private static AttendanceStatus decideByTime(LocalTime time, LocalTime attendanceCutoff, LocalTime latenessCutoff) {
        if (time.isAfter(attendanceCutoff) && time.isBefore(latenessCutoff.plusMinutes(1))) {
            return LATENESS;
        }
        if (time.isAfter(latenessCutoff)) {
            return ABSENCE;
        }
        return ATTENDANCE;
    }

    public String getDescription() {
        return status;
    }
}
