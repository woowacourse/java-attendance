package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDateTime {

    private final LocalDateTime attendedTime;

    AttendanceDateTime(LocalDateTime attendedTime) {
        AttendanceDateTimeValidator.validate(attendedTime);
        this.attendedTime = attendedTime;
    }

    public boolean isSameDay(AttendanceDateTime other) {
        LocalDate thisDate = this.attendedTime.toLocalDate();
        LocalDate otherDate = other.attendedTime.toLocalDate();
        return thisDate.isEqual(otherDate);
    }

    public LocalDate getDate() {
        return attendedTime.toLocalDate();
    }

    public LocalTime getTime() {
        return attendedTime.toLocalTime();
    }

    public static AttendanceDateTime parse(String attendedTime) {
        return new AttendanceDateTime(LocalDateTime.parse(attendedTime));
    }
}
