package attendance.domain;

import java.util.Objects;

public class Crew {

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 4;

    private final String nickname;

    public Crew(final String nickname) {
        validateLength(nickname);
        this.nickname = nickname;
    }

    private void validateLength(final String nickname) {
        if (nickname.length() < MIN_NICKNAME_LENGTH || nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new IllegalArgumentException("닉네임은 %d글자 이상 %d글자 이하만 가능합니다.".formatted(
                    MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH
            ));
        }
    }

    public boolean isSameNickname(final String otherNickname) {
        return this.nickname.equals(otherNickname);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crew crew)) {
            return false;
        }
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

}
