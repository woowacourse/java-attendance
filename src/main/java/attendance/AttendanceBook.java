package attendance;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, CrewAttendance> crews;

    public AttendanceBook() {
        this.crews = new HashMap<>();
    }

    public CrewAttendance addCrew(final String nickname) {
        Crew crew = new Crew(nickname);
        if (!crews.containsKey(crew)) {
            crews.put(crew, new CrewAttendance());
        }
        return crews.get(crew);
    }

    public void addAttendance(final String nickname, final LocalDateTime attendance) {
        CrewAttendance crewAttendance = addCrew(nickname);
        crewAttendance.add(attendance);
    }

    public int countCrews() {
        return crews.size();
    }

    public boolean isCrew(final String nickname) {
        return crews.containsKey(new Crew(nickname));
    }
}
