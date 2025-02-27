package model;

import java.util.Objects;

public class Nickname {

    private final String value;

    public Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        final String prefix = "^[가-힣]{2,4}$";
        if (!value.matches(prefix)) {
            throw new IllegalArgumentException("닉네임은 한글만 가능합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nickname n = (Nickname) o;
        return Objects.equals(value, n.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
