package domain;

import java.util.Objects;

public class CrewName implements Comparable<CrewName> {
    private final String name;

    public CrewName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSameName(final String inputName) {
        return Objects.equals(name, inputName);
    }

    @Override
    public int compareTo(final CrewName o) {
        return this.name.compareTo(o.name);
    }
}
