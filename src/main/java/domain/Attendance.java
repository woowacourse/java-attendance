package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Attendance {
    private final LocalDateTime value;

    public Attendance(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(object == null || this.getClass() != object.getClass()) return false;
        Attendance other = (Attendance) object;
        return Objects.equals(this.value, other.value);
    }
}
