package domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attendances {
    private final List<LocalTime> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void attend(LocalTime attendingTime) {
        attendances.add(attendingTime);
    }

    public boolean isAttended() {
        return !attendances.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendances that = (Attendances) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
