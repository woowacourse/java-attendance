package attendance.domain;

import java.util.Objects;

public class Month {

    private final int value;

    public Month(final int value) {
        if (value < 1 || value > 12) {
            throw new IllegalArgumentException("월은 1 이상 12 이하여야 합니다.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Month that = (Month) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
