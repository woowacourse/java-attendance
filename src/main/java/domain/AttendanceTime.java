package domain;

import java.time.LocalDateTime;

public enum AttendanceTime {
    MON(13, 5, 1),
    TUE(10, 5, 2),
    WED(10, 5, 3),
    THU(10, 5, 4),
    FRI(10, 5, 5),
    ;

    private final int hour;
    private final int minute;
    private final int dayOfWeek;

    AttendanceTime(int hour, int minute, int dayOfWeek) {
        this.hour = hour;
        this.minute = minute;
        this.dayOfWeek = dayOfWeek;

        if (hour < 8 || hour > 22) {
            throw new IllegalArgumentException("");
        }
    }

    public static boolean isAttendance(int dayOfWeek, LocalDateTime dateTime) {
        for (AttendanceTime value : values()) {
            if (value.dayOfWeek == dayOfWeek) {
                return value.hour > dateTime.getHour() || (value.hour == dateTime.getHour()
                        && value.minute >= dateTime.getMinute());
            }
        }
        return false;
    }

    public static boolean isAbsence(int dayOfWeek, LocalDateTime dateTime) {
        for (AttendanceTime value : values()) {
            if (value.dayOfWeek == dayOfWeek) {
                return value.hour > dateTime.getHour() || (value.hour == dateTime.getHour()
                        && value.minute >= dateTime.getMinute());
            }
        }
        return false;
    }

    public static boolean isTardy(int dayOfWeek, LocalDateTime dateTime) {
        for (AttendanceTime value : values()) {
            if (value.dayOfWeek == dayOfWeek) {
                return(value.hour < dateTime.getHour() && (value.hour == dateTime.getHour() && (value.minute + 25 < dateTime.getMinute())));
            }
        }
        return false;
    }
}
