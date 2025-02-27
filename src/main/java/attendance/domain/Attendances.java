package attendance.domain;

import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance attendance) {

    }

    public boolean contains(Attendance attendance) {
        return true;
    }
}
