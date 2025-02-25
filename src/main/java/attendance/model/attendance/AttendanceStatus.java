package attendance.model.attendance;

import attendance.model.campus.CampusOperationPolicy;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    ABSENCE("결석"),
    LATE("지각");

    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 6);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 31);
    private static final LocalTime WEEKDAY_LATE_TIME = LocalTime.of(10, 6);
    private static final LocalTime WEEKDAY_ABSENCE_TIME = LocalTime.of(10, 31);

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus from(
            final LocalDateTime dateTime,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        validateCampusOperationTime(dateTime, campusOperationPolicy);

        if (isMonday(dateTime.toLocalDate())) {
            return calculate(dateTime.toLocalTime(), MONDAY_LATE_TIME, MONDAY_ABSENCE_TIME);
        }
        return calculate(dateTime.toLocalTime(), WEEKDAY_LATE_TIME, WEEKDAY_ABSENCE_TIME);
    }

    private static void validateCampusOperationTime(
            final LocalDateTime dateTime,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        if (!campusOperationPolicy.isCampusOpen(dateTime)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    private static AttendanceStatus calculate(
            final LocalTime time,
            final LocalTime lateTime,
            final LocalTime absenceTime
    ) {

        if (time.isBefore(lateTime)) {
            return ATTENDANCE;
        }
        if (time.isBefore(absenceTime)) {
            return LATE;
        }
        return ABSENCE;
    }
}
