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

    public LocalDate getDate() {
        return attendedTime.toLocalDate();
    }

    public LocalTime getTime() {
        return attendedTime.toLocalTime();
    }
}
