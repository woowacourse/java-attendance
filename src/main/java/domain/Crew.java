package domain;

public record Crew(
    String name
) {

    boolean hasName(String name) {
        return this.name.equals(name);
    }
}
