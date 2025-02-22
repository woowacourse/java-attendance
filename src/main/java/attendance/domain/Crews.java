package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    private Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public static Crews fromCrewsFile(List<String> lines) {
        Set<Crew> crewNames = new HashSet<>();
        for (String line : lines) {
            crewNames.add(Crew.from(List.of(line.split(",")).get(0)));
        }
        return new Crews(crewNames);
    }

    public Crew findCrew(String crewName) {
        return crews.stream().filter(crew ->
                crew.checkSameName(crewName))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NICKNAME_NOT_PRESENCE));
    }

    public Set<Crew> getCrews() {
        return this.crews;
    }

    public void register(Map<Crew, AttendanceRegistry> register, AttendanceRegistry attendanceRegistry) {
        for (Crew crew : crews) {
            register.put(crew, attendanceRegistry);
        }
    }
}


