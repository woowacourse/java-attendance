package model;

import java.util.List;
import java.util.Objects;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Attendances targetAttendances)) {
            return false;
        }
        return attendances.containsAll(targetAttendances.attendances)
                && targetAttendances.attendances.containsAll(attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }

    public Attendance update(Attendance newAttendance) {
        return null;
    }
}
