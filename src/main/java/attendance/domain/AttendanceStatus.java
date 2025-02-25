package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    PRESENT("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus checkAttendance(LocalDateTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (time.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return checkMondayAttendance(hour, minute);
        }
        return checkRegularAttendance(hour, minute);
    }

    private static AttendanceStatus checkMondayAttendance(final int hour, final int minute) {
        if (hour <= 13 && minute <= 5) {
            return PRESENT;
        }
        if (hour == 13 && (minute <= 30)) {
            return LATENESS;
        }
        return ABSENCE;
    }

    private static AttendanceStatus checkRegularAttendance(final int hour, final int minute) {
        if ((hour == 10 && minute <= 5) || hour < 10) {
            return PRESENT;
        }
        if (hour == 10 && (minute <= 30)) {
            return LATENESS;
        }
        return ABSENCE;
    }

    public String getStatus() {
        return status;
    }

}
