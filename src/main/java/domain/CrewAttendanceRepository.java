package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrewAttendanceRepository {
    private static final CrewAttendanceRepository INSTANCE = new CrewAttendanceRepository();

    private final Map<Crew, CrewAttendance> crewAttendance = new HashMap<>();

    private CrewAttendanceRepository() {
    }

    public static CrewAttendanceRepository getInstance() {
        return INSTANCE;
    }

    public void save(CrewAttendance crewAttendance) {
        this.crewAttendance.put(crewAttendance.getCrew(), crewAttendance);
    }

    public CrewAttendance findByEqualsCrew(Crew crew) {
        if (crewAttendance.containsKey(crew)) {
            return crewAttendance.get(crew);
        }
        throw new IllegalArgumentException("해당 이름의 출석 정보가 없습니다.");
    }

    public Optional<CrewAttendance> findOptionalByCrew(Crew crew) {
        return Optional.ofNullable(crewAttendance.get(crew));
    }
    
    public List<CrewAttendance> findAll() {
        return crewAttendance.values().stream()
                .toList();
    }

    public void clear() {
        crewAttendance.clear();
    }
}
