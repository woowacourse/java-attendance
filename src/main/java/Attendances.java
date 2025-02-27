import java.time.LocalDateTime;
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

    public void checkAttendance(final String name, final LocalDateTime time) {
        Attendance crew = findCrewBy(name);
        crew.add(time);
    }

    public Attendance findCrewBy(final String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isSame(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 입니다."));
    }

    public List<Attendance> getAttendances() {
        return List.copyOf(attendances);
    }
}
