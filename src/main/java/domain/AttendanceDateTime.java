package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDateTime {

    private final LocalDate date;
    private final LocalTime time;

    private AttendanceDateTime(LocalDateTime attendedTime) {
        AttendanceDateTimeValidator.validateDateTime(attendedTime);
        this.date = attendedTime.toLocalDate();
        this.time = attendedTime.toLocalTime();
    }

    private AttendanceDateTime(LocalDate date) {
        this.date = date;
        this.time = null;
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.isEqual(date);
    }

    public AttendanceStatus getAttendanceStatus() {
        if (time == null) {
            return AttendanceStatus.ABSENCE;
        }
        return AttendanceStatus.determine(LocalDateTime.of(date, time));
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static AttendanceDateTime of(int year, int month, int dayOfMonth, int hour, int minute) {
        return new AttendanceDateTime(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
    }

    public static AttendanceDateTime ofAbsence(LocalDate date) {
        return new AttendanceDateTime(date);
    }

    public static AttendanceDateTime from(LocalDateTime localDateTime) {
        return new AttendanceDateTime(localDateTime);
    }

    public static AttendanceDateTime parse(String attendedTime) {
        return new AttendanceDateTime(LocalDateTime.parse(attendedTime));
    }
}
