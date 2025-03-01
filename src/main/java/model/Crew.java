package model;

import java.util.Objects;

public class Crew {

    private final String nickname;

    private Crew(String nickname) {
        this.nickname = nickname;
    }

    public static Crew of(String rawNickname) {
        return new Crew(rawNickname);
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Crew crew = (Crew) object;
        return Objects.equals(getNickname(), crew.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNickname());
    }
}
