package domain;

import java.time.LocalTime;

public enum AttendanceState {

    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final String description;

    AttendanceState(String description) {
        this.description = description;
    }

    public static AttendanceState findStateBy(final LocalTime localTime, final int dayOfWeekValue) {
        Calender.validateHolyDay(dayOfWeekValue);
        AttendanceTime.validateCampusTime(localTime);

        Calender calender = Calender.findBy(dayOfWeekValue);
        AttendanceTime attendanceTime = AttendanceTime.findBy(calender);

        return calculateStatusBy(localTime, attendanceTime);
    }

    private static AttendanceState calculateStatusBy(final LocalTime localTime, final AttendanceTime attendanceTime) {
        if (localTime.isAfter(attendanceTime.getLocalTimes().get(1))) {
            return ABSENCE;
        }

        if (localTime.isAfter(attendanceTime.getLocalTimes().get(0))) {
            return LATENESS;
        }

        return ATTENDANCE;
    }

    public String getDescription() {
        return description;
    }
}
