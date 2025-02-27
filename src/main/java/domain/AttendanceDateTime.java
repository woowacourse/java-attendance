package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDateTime {

    private final LocalDateTime attendedTime;

    private AttendanceDateTime(LocalDateTime attendedTime) {
        AttendanceDateTimeValidator.validate(attendedTime);
        this.attendedTime = attendedTime;
    }

    public boolean isSameDate(LocalDate date) {
        LocalDate thisDate = this.attendedTime.toLocalDate();
        return thisDate.isEqual(date);
    }

    public LocalDate getDate() {
        return attendedTime.toLocalDate();
    }

    public LocalTime getTime() {
        return attendedTime.toLocalTime();
    }

    public static AttendanceDateTime of(int year, int month, int dayOfMonth, int hour, int minute) {
        return new AttendanceDateTime(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
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

        return attendedTime.equals(that.attendedTime);
    }

    @Override
    public int hashCode() {
        return attendedTime.hashCode();
    }
}
