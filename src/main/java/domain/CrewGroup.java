package domain;

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

    public List<Crew> getAllCrews() {
        return new ArrayList<>(crews.values());
    }

    public boolean containsCrew(String name) {
        return crews.containsKey(name);
    }
}
