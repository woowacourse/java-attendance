import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void add(final Attendance attendance) {
        attendances.add(attendance);
    }

    public List<Attendance> getAttendances() {
        return List.copyOf(attendances);
    }
}
