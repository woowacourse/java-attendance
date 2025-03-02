package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attendances {
    private final List<String> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void attend(String time) {
        attendances.add(time);
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
