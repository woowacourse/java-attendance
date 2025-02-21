package domain;

import constant.CampusConstant;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석"),
    UNATTEND("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus findStatus(LocalDateTime attendanceDateTime) {
        LocalTime startTime = getStartTime(attendanceDateTime);
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();

        if (attendanceTime.isBefore(startTime.plusMinutes(CampusConstant.LATE_TIME).plusSeconds(1)) ) {
            return ATTEND;
        }
        if (attendanceTime.isAfter(startTime.plusMinutes(CampusConstant.LATE_TIME))
                && attendanceTime.isBefore(startTime.plusMinutes(CampusConstant.ABSENT_TIME).plusSeconds(1))) {
            return LATE;
        }
        return ABSENT;
    }

    private static LocalTime getStartTime(LocalDateTime attendanceDateTime) {
        LocalTime startTime = CampusConstant.STUDY_START_TIME;
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = CampusConstant.STUDY_START_TIME_MONDAY;
        }
        return startTime;
    }

    public String getStatus() {
        return status;
    }
}
