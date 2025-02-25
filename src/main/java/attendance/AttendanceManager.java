package attendance;

import java.util.HashMap;
import java.util.Map;

public class AttendanceManager {
    private final Map<Crew, AttendanceHistory> attendanceBook = new HashMap<>();

    public boolean isCrewExists(final Crew crew) {
        return attendanceBook.containsKey(crew);
    }

    public void addCrew(Crew crew) {
        attendanceBook.put(crew, new AttendanceHistory());
    }
}
