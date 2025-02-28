package domain.crew;

import dto.CrewDto;

public class Crew {

    private final String name;

    public Crew(String name) {
        this.name = name;
    }

    public boolean isCrew(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public CrewDto toDto() {
        return new CrewDto(name);
    }
}
