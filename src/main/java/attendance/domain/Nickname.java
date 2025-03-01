package attendance.domain;

import java.util.Objects;

public class Nickname {

    private final String value;

    public Nickname(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Nickname nickname)) {
            return false;
        }
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
