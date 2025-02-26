package attendance.model;

import java.util.Objects;

public class Nickname {

    private final String value;

    public Nickname(String nickname) {
        validateNickname(nickname);
        this.value = nickname;
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("닉네임이 null 또는 비어있습니다.");
        }
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임이 공백으로만 구성되어 있습니다.");
        }
        if (nickname.length() < 2 || 4 < nickname.length()) {
            throw new IllegalArgumentException("닉네임 길이는 2자 이상 4자 이하여야 합니다. 입력: %s".formatted(nickname));
        }
        if (!nickname.matches("^[가-힣]+$")) {
            throw new IllegalArgumentException("닉네임은 한글만 사용할 수 있습니다. 입력: %s".formatted(nickname));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
