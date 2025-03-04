package model;

import java.time.LocalTime;

public enum AttendanceStatusEvaluator {
    MONDAY(new AttendanceTime(LocalTime.of(13, 5)), new AttendanceTime(LocalTime.of(13, 30))),
    TUESDAY_TO_FRIDAY(new AttendanceTime(LocalTime.of(10, 5)), new AttendanceTime(LocalTime.of(10, 30)));

    private final AttendanceTime lateTime;
    private final AttendanceTime absentTime;

    AttendanceStatusEvaluator(AttendanceTime lateTime, AttendanceTime absentTime) {
        this.lateTime = lateTime;
        this.absentTime = absentTime;
    }

    public static AttendanceStatus calculateAttendanceStatus(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        if (attendanceTime.isMissingAttendanceTime()) {
            return AttendanceStatus.ABSENT;
        }
        if (attendanceDate.isMonday()) {
            return evaluateMondayAttendance(attendanceTime);
        }
        return evaluateNotMondayAttendance(attendanceTime);
    }

    private static AttendanceStatus evaluateNotMondayAttendance(AttendanceTime attendanceTime) {
        if (attendanceTime.isAfter(TUESDAY_TO_FRIDAY.absentTime)) {
            return AttendanceStatus.ABSENT;
        }
        if (attendanceTime.isAfter(TUESDAY_TO_FRIDAY.lateTime)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private static AttendanceStatus evaluateMondayAttendance(AttendanceTime attendanceTime) {
        if (attendanceTime.isAfter(MONDAY.absentTime)) {
            return AttendanceStatus.ABSENT;
        }
        if (attendanceTime.isAfter(MONDAY.lateTime)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
}
