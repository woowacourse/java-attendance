package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석")
    ;

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus determine(LocalDate inputDate, LocalTime inputTime) {
        if (EducationTime.isBeforeAttendTime(inputDate.getDayOfWeek(), inputTime)) {
            return ATTEND;
        }

        if (EducationTime.isBetweenLateTime(inputDate.getDayOfWeek(), inputTime)) {
            return LATE;
        }

        return ABSENT;
    }
}
