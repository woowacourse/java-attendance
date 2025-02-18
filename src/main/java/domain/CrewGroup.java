package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CrewGroup {
    private final Map<String, Crew> crews = new HashMap<>();

    public void addCrew(String nickname, LocalDateTime date) {
        crews.put(nickname, crews.getOrDefault(nickname, new Crew()));
        Crew crew = crews.get(nickname);
        crew.addAttendance(date);
    }

    public Crew searchCrew(String nickname) {
        return crews.getOrDefault(nickname, null);
    }
}
