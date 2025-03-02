package attendance.domain;

public record Nickname(String name) implements Comparable<Nickname> {
    @Override
    public int compareTo(Nickname o) {
        return o.name.compareTo(name);
    }
}
