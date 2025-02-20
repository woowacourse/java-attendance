package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewGroup {
    private final Map<String, Crew> crews = new HashMap<>();

    public void addCrew(String nickname, LocalDateTime date) {
        crews.put(nickname, crews.getOrDefault(nickname, new Crew(nickname)));
        Crew crew = crews.get(nickname);
        crew.addAttendance(date);
    }

    public Crew searchCrew(String nickname) {
        if (!crews.containsKey(nickname)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
        return crews.get(nickname);
    }

    public void addAllAbsent(LocalDateTime today) {
        crews.values()
                .forEach(crew -> crew.addAbsent(today));
    }

    public void calculateAllAttendanceCount() {
        crews.values()
                .forEach(Crew::updateAttendanceCount);
    }

    public List<Crew> getAllAttendanceAlertLevel() {
        return crews.values()
                .stream()
                .filter(crew -> !crew.calculateAttendanceAlertLevel().equals(AttendanceAlertLevel.NORMAL))
                .toList();
    }
}
