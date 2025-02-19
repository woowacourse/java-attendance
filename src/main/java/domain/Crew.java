package domain;

public class Crew {

    private final String name;

    public Crew(final String name) {
        this.name = name;
    }

    public static Crew from(final String name) {
        return new Crew(name);
    }

    public String getName() {
        return this.name;
    }
}
