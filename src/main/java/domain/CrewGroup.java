package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewGroup {

    private final Map<String, Crew> crews;

    public CrewGroup() {
        this.crews = new HashMap<>();
    }

    public void addCrew(String name, Crew crew) {
        crews.putIfAbsent(name, crew);
    }

    public Crew findCrewByName(String name) {
        if (!crews.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return crews.get(name);
    }

    public List<Crew> getCrewsAtRisk(LocalDate nowDate) {
        List<Crew> crewsAtRisk = new ArrayList<>();
        for (Crew crew : crews.values()) {
            addCrewIfAtRisk(nowDate, crew, crewsAtRisk);
        }
        return crewsAtRisk;
    }

    public boolean containsCrew(String name) {
        return crews.containsKey(name);
    }

    private void addCrewIfAtRisk(LocalDate nowDate, Crew crew, List<Crew> crewsAtRisk) {
        if (determinePenaltyStatus(crew.getName(), nowDate) != Penalty.PASS) {
            crewsAtRisk.add(crew);
        }
    }

    private Penalty determinePenaltyStatus(String name, LocalDate nowDate) {
        return findCrewByName(name).determinePenaltyStatus(nowDate);
    }
}
