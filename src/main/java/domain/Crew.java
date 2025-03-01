package domain;

public class Crew {

    private final String name;

    private Crew(String name) {
        this.name = name;
    }

    public static Crew fromName(String name) {
        return new Crew(name);
    }

    public String getName() {
        return name;
    }
}
