package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DummyAttendanceDateTime extends AttendanceDateTime {

    protected DummyAttendanceDateTime(LocalDate date) {
        super(date);
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public LocalTime getTime() {
        return null;
    }

    @Override
    public AttendanceStatus getAttendanceStatus() {
        return AttendanceStatus.ABSENCE;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof DummyAttendanceDateTime that)) {
            return false;
        }

        return Objects.equals(date, that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
