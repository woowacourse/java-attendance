package attendance.repository;

import attendance.domain.Attendance;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Set<String> getUniqueNames() {
        Set<String> names = new HashSet<>();
        for (Attendance attendance : attendances) {
            names.add(attendance.getCrewName());
        }
        return names;
    }
}
