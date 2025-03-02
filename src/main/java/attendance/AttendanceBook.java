package attendance;

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

    public int countCrews() {
        return crews.size();
    }

    public boolean isCrew(final String nickname) {
        return crews.containsKey(new Crew(nickname));
    }
}
