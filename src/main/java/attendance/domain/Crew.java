package attendance.domain;

import java.util.Objects;

public class Crew {

    private final Nickname nickname;

    public Crew(Nickname nickname) {
        this.nickname = nickname;
    }

    public Nickname getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Crew crew = (Crew) object;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
