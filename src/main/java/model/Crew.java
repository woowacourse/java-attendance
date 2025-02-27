package model;

import java.util.Objects;

public class Crew {

    private final Nickname nickname;

    private Crew(final Nickname nickname) {
        this.nickname = nickname;
    }

    public static Crew of(final String nicknameInput) {
        final Nickname nickname = new Nickname(nicknameInput);
        return new Crew(nickname);
    }

    public Nickname getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
