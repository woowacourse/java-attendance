package attendance.domain;

import java.util.Objects;

public class Nickname implements Comparable {
    final String nickname;

    public Nickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
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
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return 0;
        }
        Nickname that = (Nickname) o;
        return that.nickname.compareTo(this.nickname);
    }
}
