package model;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석");

    private final String attendanceStatus;

    AttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceStatus attendanceStatusCalculate(LocalDate todayDate, LocalTime attendanceTime) {
        LocalTime attendanceStartTime = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(todayDate);
        if (attendanceTime.isAfter(attendanceStartTime.plusMinutes(30))) {
            return ABSENT;
        }
        if (attendanceTime.isAfter(attendanceStartTime.plusMinutes(5))) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
