package domain;

public record CrewName(String value) implements Comparable<CrewName> {
    @Override
    public int compareTo(CrewName other) {
        return this.value.compareTo(other.value);
    }
}
