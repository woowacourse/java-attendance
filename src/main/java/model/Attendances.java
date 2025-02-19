package model;

import java.util.Collections;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public boolean contains(Attendance attendance) {
        return attendances.stream().anyMatch(attendance::equals);
    }

    public void checkIn(Attendance attendance) {
        attendances.add(attendance);
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
