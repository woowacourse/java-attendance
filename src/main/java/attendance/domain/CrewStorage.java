package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class CrewStorage {
    private final List<String> crews = new ArrayList<>();

    public void addCrew(String name) {
        crews.add(name);
    }

    public boolean isContained(String name) {
        return crews.contains(name);
    }
}
