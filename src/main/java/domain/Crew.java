package domain;

public class Crew {

    private final String name;

    public Crew(String name) {
        this.name = name;
    }

    boolean hasName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }
}
