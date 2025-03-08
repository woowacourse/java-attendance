package domain.crew;

import java.util.Objects;

public class Nickname implements Comparable<Nickname> {
    private static final String NICKNAME_REGEX = "^[가-힣]{2,4}$";

    private final String value;

    public Nickname(final String nickname) {
        validate(nickname);
        this.value = nickname;
    }

    public String getValue() {
        return value;
    }

    private void validate(final String nickname) {
        if (!nickname.matches(NICKNAME_REGEX)) {
            throw new IllegalArgumentException("닉네임은 2~4글자이며 한글로만 작성이 가능합니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(final Nickname o) {
        return value.compareTo(o.value);
    }
}
