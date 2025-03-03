package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final LocalDateTime dateTime;
    private final AttendanceState state;

    private Attendance(final LocalDateTime dateTime, final AttendanceState state) {
        this.dateTime = dateTime;
        this.state = state;
    }

    public static Attendance createFromDateTime(final LocalDateTime dateTime) {
        AttendanceState state = AttendanceState.evaluate(dateTime);
        return new Attendance(dateTime, state);
    }

    public boolean isSameDate(final LocalDate date) {
        return this.dateTime.toLocalDate().equals(date);
    }

    public boolean isNotDefaultTime() {
        return !dateTime.toLocalTime().equals(LocalTime.MAX);
    }

    public boolean isBefore(final LocalDate date) {
        return dateTime.toLocalDate().isBefore(date);
    }

    public boolean isSameState(final AttendanceState state) {
        return this.state.equals(state);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceState getState() {
        return state;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Attendance o = (Attendance) object;
        return dateTime.equals(o.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dateTime);
    }
}
