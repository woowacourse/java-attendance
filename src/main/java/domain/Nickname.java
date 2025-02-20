package domain;

import error.CustomIllegalArgumentException;
import java.util.Objects;
import java.util.regex.Pattern;

public class Nickname implements Comparable<Nickname> {

    private final String nickname;

    public Nickname(final String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(final String nickname) {
        String regex = "^[가-힣]{2,4}$";
        final boolean matches = Pattern.matches(regex, nickname);
        if (!matches) {
            throw new CustomIllegalArgumentException("한글 이름이어야 합니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Nickname nickname1 = (Nickname) o;
        return Objects.equals(nickname, nickname1.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

    @Override
    public int compareTo(final Nickname o) {
        return this.nickname.compareTo(o.getNickname());
    }
}
