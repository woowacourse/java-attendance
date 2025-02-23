package attendance.model;

import java.util.Objects;

public class Crew {

    private final Nickname nickname;

    public Crew(Nickname nickname) {
        this.nickname = nickname;
    }

    public boolean isEqualsNickname(Nickname nickname) {
        return this.nickname.equals(nickname);
    }

    public Nickname getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
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
