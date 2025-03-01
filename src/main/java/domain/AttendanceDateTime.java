package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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

    public boolean isBetweenDates(LocalDate fromInclusive, LocalDate toInclusive) {
        return date.equals(fromInclusive)
            || date.equals(toInclusive)
            || (date.isAfter(fromInclusive) && date.isBefore(toInclusive));
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AttendanceDateTime that)) {
            return false;
        }

        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(date);
        result = 31 * result + Objects.hashCode(time);
        return result;
    }
}
