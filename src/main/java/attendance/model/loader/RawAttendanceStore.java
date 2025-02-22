package attendance.model.loader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RawAttendanceStore {
    private final List<RawAttendanceEntry> rawEntries = new ArrayList<>();

    public void add(String crewName, LocalDateTime dateTime) {
        rawEntries.add(new RawAttendanceEntry(crewName, dateTime));
    }

    public List<RawAttendanceEntry> getAllEntries() {
        return rawEntries;
    }

    public record RawAttendanceEntry(String crewName, LocalDateTime dateTime) {
    }
}
