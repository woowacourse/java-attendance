package attendance.domain;

import static attendance.domain.CrewStatus.*;
import static attendance.error.ErrorMessage.INVALID_CREW_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    private Crews() {
        crews = new HashSet<>();
    }

    public static Crews create() {
        return new Crews();
    }

    public boolean addCrew(Crew crew) {
        return crews.add(crew);
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Crew findByCrewName(String name) {
        return crews.stream()
            .filter(crew -> crew.isSameCrew(name))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_CREW_NAME.getMessage()));
    }

    public List<Crew> findDangerousCrew(CrewAttendanceManager crewAttendanceManager) {
        List<Crew> dangerousCrews = new ArrayList<>();
        for (Crew crew : crews) {
            addDangerousCrew(crewAttendanceManager, crew, dangerousCrews);
        }
        return dangerousCrews;
    }

    private void addDangerousCrew(CrewAttendanceManager crewAttendanceManager, Crew crew,
        List<Crew> dangerousCrews) {
        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);
        Map<AttendanceType, Integer> attendanceResult = attendanceHistories.calculateAttendanceResult();
        CrewStatus crewStatus = calculateCrewStatus(attendanceResult);
        if (crewStatus != CLEAR) {
            dangerousCrews.add(crew);
        }
    }
}
