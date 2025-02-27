import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendance {

    private final Crew crew;
    private final List<LocalDateTime> attendanceTime;

    public Attendance(final Crew crew, final List<LocalDateTime> attendanceTime) {
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }

    public void add(final LocalDateTime time) {
        attendanceTime.add(time);
    }

    public List<LocalDateTime> getAttendanceTime() {
        return new ArrayList<>(attendanceTime);
    }

    public boolean isSame(final String name) {
        return this.crew.isSame(name);
    }
}
