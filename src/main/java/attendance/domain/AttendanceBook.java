package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final String crewName;
    private final Map<LocalDate, LocalTime> timestamps;

    public AttendanceBook(String crewName) {
        this.crewName = crewName;
        this.timestamps = new HashMap<>();
    }

    public boolean isNameMatched(String crewName) {
        return this.crewName.equals(crewName);
    }

}
