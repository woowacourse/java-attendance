package attendance.domain;

import java.util.Objects;

public class Crew {

    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int MAXIMUM_NICKNAME_LENGTH = 4;
    private final String nickname;

    public Crew(String nickname) {
        validateLength(nickname);
        this.nickname = nickname;
    }

    private void validateLength(String nickname) {
        if (nickname.length() < MINIMUM_NICKNAME_LENGTH || nickname.length() > MAXIMUM_NICKNAME_LENGTH) {
            throw new IllegalArgumentException(
                    "닉네임은 %d글자 이상, %d글자 이하만 가능합니다.".formatted(MINIMUM_NICKNAME_LENGTH, MAXIMUM_NICKNAME_LENGTH)
            );
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
        Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

}
