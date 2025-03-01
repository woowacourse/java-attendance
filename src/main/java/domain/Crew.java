package domain;

import java.util.Objects;

public class Crew {

    private final String nickname;

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
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
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
