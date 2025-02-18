package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository() {
        this.attendances = new ArrayList<>();
    }

}
