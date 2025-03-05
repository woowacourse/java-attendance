package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceState {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private String state;

    AttendanceState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static AttendanceState findStateBy(LocalDate localDate, LocalTime localTime) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (checkAbsenceDay(localTime)) {
            return AttendanceState.ABSENCE;
        }

        if (dayOfWeek == DayOfWeek.MONDAY) {
            if (localTime.isAfter(LocalTime.of(13, 30))) {
                return ABSENCE;
            }
            if (localTime.isAfter(LocalTime.of(13, 5))) {
                return LATENESS;
            }
            return ATTENDANCE;
        }
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            if (localTime.isAfter(LocalTime.of(10, 30))) {
                return ABSENCE;
            }
            if (localTime.isAfter(LocalTime.of(10, 5))) {
                return LATENESS;
            }
        }
        return ATTENDANCE;
    }

    private static boolean checkAbsenceDay(LocalTime localTime) {
        return localTime.equals(LocalTime.of(0, 0));
    }
}
