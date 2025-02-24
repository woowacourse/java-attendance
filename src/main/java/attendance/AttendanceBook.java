package attendance;

import java.time.LocalDate;
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
        if (existsByDate(nickname, dateTime)) {
            throw new IllegalArgumentException();
        }
        List<LocalDateTime> attendances = crewAttendances.get(nickname);
        attendances.add(dateTime);
        crewAttendances.put(nickname, attendances);
        return dateTime;
    }

    private boolean existsByDate(String nickname, LocalDateTime dateTime) {
        List<LocalDateTime> attendances = crewAttendances.get(nickname);
        LocalDate date = dateTime.toLocalDate();
        return attendances.stream()
                .map(LocalDate::from)
                .anyMatch(date::equals);
    }
}
