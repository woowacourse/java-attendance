package attendance.repository;

import attendance.domain.Attendance;
import java.util.List;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
