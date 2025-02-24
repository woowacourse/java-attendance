package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;

    public Attendance(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Attendance that)) {
            return false;
        }
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
