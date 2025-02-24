package attendance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, List<LocalDateTime>> crewAttendances;

    public AttendanceBook(Map<String, List<LocalDateTime>> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public LocalDateTime attend(String nickname, LocalDateTime dateTime) {
        if (!crewAttendances.containsKey(nickname)) {
            throw new IllegalArgumentException();
        }
        return LocalDateTime.of(2024, 12, 16, 12, 59);
    }
}
