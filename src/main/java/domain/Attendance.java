package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Attendance {

    private static Map<Crew, List<LocalDateTime>> attendanceMap;

    public Attendance(final Map<Crew, List<LocalDateTime>> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

    public Crew getCrewByName(String name) {
        return attendanceMap.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
