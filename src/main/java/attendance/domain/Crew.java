package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_NAME_LENGTH;
import static attendance.error.ErrorMessage.ERROR_NAME_NULL_OR_BLANK;

import java.util.Objects;

public class Crew {
    private final String name;

    public Crew(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateNullOrBlank(name);
        validateLength(name);
    }

    private void validateNullOrBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ERROR_NAME_NULL_OR_BLANK);
        }
    }

    private void validateLength(String name) {
        if (name.length() < 2 || name.length() > 4) {
            throw new IllegalArgumentException(ERROR_NAME_LENGTH);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}