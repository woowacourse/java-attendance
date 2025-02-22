package attendance.model.loader;

import attendance.model.Crew;
import attendance.model.Crews;

public class CrewRegistry {
    private final Crews crews;

    public CrewRegistry(Crews crews) {
        this.crews = crews;
    }

    public Crew findCrewOrCreate(String name) {
        return crews.getCrews().stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseGet(() -> createAndRegisterCrew(name));
    }

    private Crew createAndRegisterCrew(String name) {
        Crew newCrew = new Crew(name);
        crews.add(newCrew);
        return newCrew;
    }

}

