package domain;

public class Crew {
    private final String name;

    public Crew(final String name) {
        this.name = name;
    }

    public boolean matchName(final String crewName) {
        return this.name.equals(crewName);
    }

    public String getName() {
        return this.name;
    }
}
