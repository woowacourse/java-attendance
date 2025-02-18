import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository() {
        this.attendances = new ArrayList<>();
    }

    public void save(Attendance attendance) {
        attendances.add(attendance);
    }

    public List<Attendance> findByCrew(Crew crew) {
        return attendances.stream().filter(attendance -> attendance.getCrew().equals(crew)).toList();
    }
}
