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
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return crews.get(nickname);
    }

    public void addAllAbsent(LocalDateTime today) {
        crews.values()
                .forEach(crew -> crew.addAbsent(today));
    }

    public List<Crew> getAllAttendanceAlertLevel() {
        return crews.values()
                .stream()
                .filter(crew -> !crew.getAttendanceAlertLevel().equals(AttendanceAlertLevel.NORMAL))
                .toList();
    }
}
