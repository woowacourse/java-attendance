import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    List<Attendance> attendances;

    public AttendanceRepository() {
        this.attendances = new ArrayList<>();
    }

    public void save(Attendance attendance) {
        attendances.add(attendance);
    }
}
