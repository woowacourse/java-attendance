package attendance.domain;

import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, List<AttendanceDateTime>> crewAttedances;

    public AttendanceBook(final Map<Crew, List<AttendanceDateTime>> crewAttendances) {
        this.crewAttedances = crewAttendances;
    }
}
