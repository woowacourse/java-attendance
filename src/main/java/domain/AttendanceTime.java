package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AttendanceTime {

    private final LocalTime localtime;

    public AttendanceTime(final LocalTime localtime) {
        this.localtime = localtime;
    }

    public static AttendanceTime of(final DayOfWeek dayOfWeek, final LocalTime localtime) {
        validateTime(dayOfWeek, localtime);
        return new AttendanceTime(localtime);
    }


    private static void validateTime(final DayOfWeek dayOfWeek, final LocalTime localtime) {
        if (CampusTime.isNotOpenTime(dayOfWeek, localtime)) {
            throw new IllegalArgumentException();
        }
    }

    public LocalTime getLocaltime() {
        return localtime;
    }
}
