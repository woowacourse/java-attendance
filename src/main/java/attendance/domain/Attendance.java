package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final LocalDateTime dateTime;
    private final AttendanceState state;

    public Attendance(final LocalDateTime dateTime) {
        this.dateTime = dateTime;

        int overTime = ClassTime.calculateAttendanceDifference(dateTime);
        this.state = AttendanceState.evaluate(overTime);
    }

    public boolean isSameDate(final LocalDate date) {
        return this.dateTime.toLocalDate().equals(date);
    }

    public boolean isNotDefaultTime() {
        return !dateTime.toLocalTime().equals(LocalTime.MAX);
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
