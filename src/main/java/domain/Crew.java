package domain;

public class Crew {

    private final String name;
    private final Attendances attendances;

    public Crew(String name, Attendances attendances) {
        this.name = name;
        this.attendances = attendances;
    }

    public CrewStatus getCrewStatus() {
        return this.attendances.getCrewStatue();
    }
}
