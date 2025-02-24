package attendance.domain;

import java.util.Objects;

public final class Crew {

    private final String nickname;

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Crew crew = (Crew) object;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }
}
