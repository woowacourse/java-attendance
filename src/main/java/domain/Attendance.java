package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final LocalDate localDate;
    private final LocalTime localTime;

    public Attendance(LocalDate localDate, LocalTime localTime) {
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public Attendance updateTime(LocalTime newTime) {
        return new Attendance(localDate, newTime);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(localDate, that.localDate) && Objects.equals(localTime, that.localTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDate, localTime);
    }
}
