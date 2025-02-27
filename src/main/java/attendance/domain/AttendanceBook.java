package attendance.domain;

import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<String, List<AttendanceDateTime>> crewAttedances;

    public AttendanceBook(Map<String, List<AttendanceDateTime>> crewAttendances) {
        this.crewAttedances = crewAttendances;
    }
}
