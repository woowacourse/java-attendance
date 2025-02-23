 package attendance.domain;

import static attendance.domain.CrewStatus.CLEAR;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

 public class CrewManager {
     private final Set<Crew> crews = new HashSet<>();

    public boolean addCrew(Crew crew) {
        return crews.add(crew);
    }

    public Crew findByCrewName(String name){
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가진 크루는 없음"));
    }

    public List<Crew> getDangerousCrews(LocalDate localDate) {
        List<Crew> dangerousCrews = new ArrayList<>();
        for (Crew crew : crews) {
            Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(localDate);
            CrewStatus crewStatus = crew.calculateCrewStatus(attendanceResult);
            if (crewStatus == CLEAR) {
                continue;
            }
            dangerousCrews.add(crew);
        }
        return dangerousCrews;
    }
}
