package attendance.domain.constant;

import attendance.domain.CampusTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석"),
    HOLIDAY("휴일");

    public static final int MONDAY_LIMIT_HOUR = 13;
    public static final int WEEKDAY_LIMIT_HOUR = 10;
    private final String status;

    AttendanceStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static AttendanceStatus calculateAttendanceStatus(final LocalDate localDate, final CampusTime campusTime) {
        if (isHoliday(localDate)) {
            return AttendanceStatus.HOLIDAY;
        }
        if (isMonday(localDate)) {
            return compareAttendanceStatus(MONDAY_LIMIT_HOUR, campusTime);
        }
        return compareAttendanceStatus(WEEKDAY_LIMIT_HOUR, campusTime);
    }

    private static AttendanceStatus compareAttendanceStatus(int hour, CampusTime campusTime) {
        if (campusTime.isAfter(LocalTime.of(hour, 30))) {
            return AttendanceStatus.ABSENCE;
        }
        if (campusTime.isAfter(LocalTime.of(hour, 5))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private static boolean isHoliday(LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SUNDAY || localDate.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    private static boolean isMonday(LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.MONDAY;
    }

}
