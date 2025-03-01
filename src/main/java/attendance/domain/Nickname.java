package attendance.domain;

import static attendance.exception.ErrorMessage.INVALID_NICKNAME;

import java.util.Objects;

public class Nickname implements Comparable<Nickname> {
    private static final String WHITE_SPACE = " ";

    final String nickname;

    public Nickname(final String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    private void validate(final String nickname) {
        if (nickname == null ||
                nickname.isBlank() ||
                hasBlank(nickname)) {
            throw new IllegalArgumentException(INVALID_NICKNAME.getMessage());
        }
    }

    private boolean hasBlank(final String nickname) {
        return !nickname.trim().equals(nickname) ||
                nickname.split(WHITE_SPACE).length != 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nickname crew = (Nickname) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public int compareTo(Nickname given) {
        if (given == null || getClass() != given.getClass()) {
            return 0;
        }
        return given.nickname.compareTo(this.nickname);
    }
}
