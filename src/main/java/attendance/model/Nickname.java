package attendance.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Nickname implements Comparable<Nickname> {

    private static final Pattern KOREAN_ONLY_PATTERN = Pattern.compile("^[가-힣]+$");
    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 4;

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
        if (nickname.length() < MIN_NICKNAME_LENGTH || MAX_NICKNAME_LENGTH < nickname.length()) {
            throw new IllegalArgumentException("닉네임 길이는 %d자 이상 %d자 이하여야 합니다. 입력: %s"
                    .formatted(MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH, nickname));
        }
        if (!KOREAN_ONLY_PATTERN.matcher(nickname).matches()) {
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

    @Override
    public int compareTo(Nickname other) {
        return this.value.compareTo(other.value);
    }
}
