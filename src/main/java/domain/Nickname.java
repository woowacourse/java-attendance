package domain;

import java.util.Objects;

public class Nickname implements Comparable<Nickname> {

    private final String nickname;

    public Nickname(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    public void validate(String nickname) {
        validateNonBlank(nickname);
        validateLength(nickname);

    }

    public void validateNonBlank(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 닉네임은 공백일 수 없습니다.");
        }
    }

    public void validateLength(String nickname) {
        if (nickname.length() > 3) {
            throw new IllegalArgumentException("[ERROR] 닉네임의 길이는 3자 이하여야 합니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Nickname nickname1 = (Nickname) object;
        return Objects.equals(nickname, nickname1.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

    @Override
    public int compareTo(Nickname o) {
        return this.nickname.compareTo(o.getNickname());
    }

    @Override
    public String toString() {
        return nickname;
    }
}
