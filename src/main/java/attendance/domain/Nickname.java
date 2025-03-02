package attendance.domain;

public record Nickname(String name) implements Comparable<Nickname> {
    @Override
    public int compareTo(Nickname o) {
        return name.compareTo(o.name);
    }
}
