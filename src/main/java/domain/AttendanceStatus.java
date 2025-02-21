package domain;


import static util.Constants.ABSENT_CONDITION;
import static util.Constants.LATE_CONDITION;

import dto.AttendanceCount;
import dto.AttendanceData;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTEND("출석"), LATE("지각"), ABSENCE("결석");

    private final String result;

    AttendanceStatus(String result) {
        this.result = result;
    }

    public static AttendanceCount getCount(AttendanceData attendanceData) {
        int attendanceCount = 0;
        int lateCount = 0;
        int absentCount = 0;
        for(Attendance attendance : attendanceData.getValue()) {
            if(AttendanceStatus.of(attendance) == ATTEND) {
                attendanceCount++;
            }
            if(AttendanceStatus.of(attendance) == LATE) {
                lateCount++;
            }
            if(AttendanceStatus.of(attendance) == ABSENCE) {
                absentCount++;
            }
        }
        return new AttendanceCount(attendanceCount, lateCount, absentCount);
    }

    public static AttendanceStatus of(LocalDateTime localDateTime) {
        return of(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getDayOfWeek());
    }

    public static AttendanceStatus of(int hour, int minute, DayOfWeek dayOfWeek) {
        int startHour = 10;
        if (dayOfWeek == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        if (hour < startHour || (hour == startHour && minute < LATE_CONDITION)) {
            return ATTEND;
        }
        if ((hour == startHour) && minute < ABSENT_CONDITION) {
            return LATE;
        }
        return ABSENCE;
    }

    public String getResult() {
        return result;
    }

    private static AttendanceStatus of(Attendance attendance) {
        return AttendanceStatus.of(attendance.dateAndTime());
    }
}
