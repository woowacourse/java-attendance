package domain;


import static util.Constants.ABSENT_CONDITION;
import static util.Constants.LATE_CONDITION;

import dto.AttendanceCount;
import dto.AttendanceData;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum Status {
    ATTEND("출석"), LATE("지각"), ABSENCE("결석");

    private final String result;

    Status(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public static AttendanceCount getCount(AttendanceData attendanceData) {
        int attendanceCount = 0;
        int lateCount = 0;
        int absentCount = 0;
        for(Attendance attendance : attendanceData.value()) {
            if(Status.of(attendance) == ATTEND) {
                attendanceCount++;
            }
            if(Status.of(attendance) == LATE) {
                lateCount++;
            }
            if(Status.of(attendance) == ABSENCE) {
                absentCount++;
            }
        }
        return new AttendanceCount(attendanceCount, lateCount, absentCount);
    }

    public static Status of(LocalDateTime localDateTime) {
        return of(localDateTime.getHour(),
                localDateTime.getMinute(),
                localDateTime.getDayOfWeek());
    }

    public static Status of(int hour, int minute, DayOfWeek dayOfWeek) {
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

    private static Status of(Attendance attendance) {
        return Status.of(attendance.dateAndTime());
    }
}
