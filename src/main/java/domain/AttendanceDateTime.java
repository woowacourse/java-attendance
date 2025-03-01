package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public abstract class AttendanceDateTime {

    protected final LocalDate date;

    protected AttendanceDateTime(LocalDate date) {
        AttendanceDateTimeValidator.validateDate(date);
        this.date = date;
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.isEqual(date);
    }

    public boolean isBetweenDates(LocalDate fromInclusive, LocalDate toInclusive) {
        return date.equals(fromInclusive)
            || date.equals(toInclusive)
            || (date.isAfter(fromInclusive) && date.isBefore(toInclusive));
    }

    public abstract LocalDate getDate();

    public abstract LocalTime getTime();

    public abstract AttendanceStatus getAttendanceStatus();

    public static AttendanceDateTime from(LocalDateTime dateTime) {
        return new RealAttendanceDateTime(dateTime);
    }

    public static AttendanceDateTime ofAbsence(LocalDate date) {
        return new DummyAttendanceDateTime(date);
    }

    public static AttendanceDateTime of(int year, int month, int day, int hour, int minute) {
        return new RealAttendanceDateTime(LocalDateTime.of(year, month, day, hour, minute));
    }

    public static AttendanceDateTime parse(String dateTime) {
        return new RealAttendanceDateTime(LocalDateTime.parse(dateTime));
    }
}
