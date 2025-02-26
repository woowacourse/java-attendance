package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import util.DateTimeUtil;

public enum AttendanceState {

    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final String description;

    AttendanceState(String description) {
        this.description = description;
    }

    public static AttendanceState findStateBy(final LocalTime localTime, final LocalDate localDate) {
        DateTimeUtil.validateHoliDay(DateTimeUtil.getDateBy(localDate));

        if (checkAbsenceDay(localTime)) {
            return AttendanceState.ABSENCE;
        }
        AttendanceTime.validateCampusTime(localTime);

        return getDayOfWeekString(localTime, localDate);
    }

    private static boolean checkAbsenceDay(LocalTime localTime) {
        return localTime.equals(LocalTime.of(0, 0));
    }

    private static AttendanceState getDayOfWeekString(LocalTime localTime, LocalDate localDate) {
        if (localDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            return determineAttendanceStatus(localTime, AttendanceTime.MON_TIME);
        }

        if (!(localDate.getDayOfWeek() == DayOfWeek.SATURDAY) && !(localDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
            return determineAttendanceStatus(localTime, AttendanceTime.ELSE_TIME);
        }

        return null;
    }

    private static AttendanceState determineAttendanceStatus(LocalTime localTime, AttendanceTime time) {
        if (localTime.isAfter(time.getLocalTimes().get(1))) {
            return ABSENCE;
        } else if (localTime.isAfter(time.getLocalTimes().get(0))) {
            return LATENESS;
        }
        return ATTENDANCE;
    }

    public String getDescription() {
        return description;
    }
}
