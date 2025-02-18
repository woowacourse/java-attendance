package domain;

import java.util.regex.Pattern;

public class Nickname {
    private final String nickname;

    public Nickname(final String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(final String nickname) {
        String regex = "^[가-힣]{2,4}$";
        final boolean matches = Pattern.matches(regex, nickname);
        if (!matches) {
            throw new IllegalArgumentException("한글 이름이어야 합니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }
}
