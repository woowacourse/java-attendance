package attendance.model;

import java.util.Objects;

public class Nickname {

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 4;

    private final String value;

    public Nickname(String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("닉네임이 비어있습니다.");
        }
        if (value.length() < MIN_NICKNAME_LENGTH || value.length() > MAX_NICKNAME_LENGTH) {
            throw new IllegalArgumentException(
                    "닉네임은 %d~%d 사이의 글자수여야 합니다.".formatted(MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH));
        }
    }

    public String getValue() {
        return value;
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
}
