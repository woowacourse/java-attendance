package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final LocalDateTime value;

    public Attendance(LocalDateTime value) {
        this.value = value;
    }

    public DayOfWeek getDayOfWeek() {
        return value.getDayOfWeek();
    }

    public LocalTime getTime() {
        return value.toLocalTime();
    }

    public boolean isEqualDate(LocalDate targetDate) {
        return value.toLocalDate().equals(targetDate);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Attendance other = (Attendance) object;
        return Objects.equals(value.toLocalDate(), other.value.toLocalDate());
    }
}
