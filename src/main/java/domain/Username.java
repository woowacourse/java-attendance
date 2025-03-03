package domain;

import java.util.Objects;

public class Username implements Comparable<Username> {
    private final String name;

    private static final int NAME_MAX_LENGTH = 5;

    public Username(String name) {
        validateNameLength(name);
        this.name = name;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    private void validateNameLength(String username) {
        if (username.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 크루 이름은 최대 5글자입니다.");
        }
    }

    @Override
    public int compareTo(Username o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Username username = (Username) o;
        return Objects.equals(name, username.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String getName() {
        return name;
    }
}
