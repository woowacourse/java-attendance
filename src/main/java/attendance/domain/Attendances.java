package attendance.domain;

import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean add(Attendance attendance) {
        return attendances.add(attendance);
    }
}
