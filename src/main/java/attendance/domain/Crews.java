package attendance.domain;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public void add(Crew crew) {
        int index = crews.indexOf(crew);
        if (crews.contains(crew)) {
            crews.set(index, crew);
            return;
        }
        crews.add(crew);
    }

    public int size() {
        return crews.size();
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }
}
