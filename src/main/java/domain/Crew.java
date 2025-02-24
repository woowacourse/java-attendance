package domain;

import java.util.Objects;

public class Crew {
    private final String nickName;

    private Crew(String nickName) {
        this.nickName = nickName;
    }

    public static Crew of(String nickName) {
        if (nickName.length() < 2 || nickName.length() > 4) {
            throw new IllegalArgumentException();
        }
        return new Crew(nickName);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Crew crew)) {
            return false;
        }
        return Objects.equals(nickName, crew.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickName);
    }
}
