package domain;

import java.util.Objects;

class CrewName {
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
}
