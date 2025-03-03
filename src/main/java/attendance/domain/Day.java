package attendance.domain;

import java.util.Objects;

public class Day {

    private final int value;

    public Day(final int value) {
        if (value < 1 || value > 31) {
            throw new IllegalArgumentException("일은 1 이상 31 이하여야 합니다.");
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
        Day that = (Day) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
