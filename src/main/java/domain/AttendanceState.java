package domain;

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
        DateTimeUtil.validateHolyDay(DateTimeUtil.getDateBy(localDate)); // 크리스마스 고려 안 됨...

        if (checkAbsenceDay(localTime)) {
            return AttendanceState.ABSENCE;
        }
        AttendanceTime.validateCampusTime(localTime);

        String dayOfWeek = DateTimeUtil.getDayOfWeekBy(
                LocalDate.of(
                        DateTimeUtil.getYearBy(localDate),
                        DateTimeUtil.getMonthBy(localDate),
                        DateTimeUtil.getDateBy(localDate)));

        return getDayOfWeekString(localTime, dayOfWeek);
    }

    private static boolean checkAbsenceDay(LocalTime localTime) {
        return localTime.equals(LocalTime.of(0, 0));
    }

    private static AttendanceState getDayOfWeekString(LocalTime localTime, String dayOfWeek) {
        if (dayOfWeek.equals("월요일")) {
            return determineAttendanceStatus(localTime, AttendanceTime.MON_TIME);
        }

        if (!dayOfWeek.equals("공휴일")) {
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
