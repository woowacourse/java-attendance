package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class RealAttendanceDateTime extends AttendanceDateTime {

    private final LocalTime time;

    protected RealAttendanceDateTime(LocalDateTime attendedTime) {
        super(attendedTime.toLocalDate());
        LocalTime time = attendedTime.toLocalTime();
        AttendanceDateTimeValidator.validateTime(time);
        this.time = time;
    }

    public AttendanceStatus getAttendanceStatus() {
        return AttendanceStatus.determine(LocalDateTime.of(date, time));
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof RealAttendanceDateTime that)) {
            return false;
        }

        return Objects.equals(date, that.getDate()) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(date);
        result = 31 * result + Objects.hashCode(time);
        return result;
    }
}
