package attendance.model;

import java.util.HashSet;
import java.util.Set;

public class Attendances {

    private final Set<Attendance> attendances;

    public Attendances(Set<Attendance> attendances) {
        this.attendances = new HashSet<>(attendances);
    }

    public void add(Attendance attendance) {
        if (attendances.contains(attendance)) {
            throw new IllegalArgumentException("크루는 같은 날에 또 출석할 수 없습니다.");
        }
        attendances.add(attendance);
    }
}
