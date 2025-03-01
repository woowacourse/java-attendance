package attendance.domain;

import java.util.Objects;

public class Nickname implements Comparable {
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
                nickname.trim().length() != nickname.length() ||
                nickname.split(" ").length != 1) {
            throw new IllegalArgumentException("닉네임은 1글자 이상 필수이며 공백을 포함할 수 없습니다.");
        }
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
