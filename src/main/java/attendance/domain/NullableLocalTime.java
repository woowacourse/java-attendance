package attendance.domain;

import java.time.LocalTime;
import java.util.Objects;

public abstract class NullableLocalTime {
    private final LocalTime time;

    public NullableLocalTime(LocalTime time) {
        this.time = time;
    }

    public abstract boolean isPresent();

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NullableLocalTime that = (NullableLocalTime) o;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
