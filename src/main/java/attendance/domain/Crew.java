package attendance.domain;

import java.util.Objects;

public class Crew {

    private final String nickname;

    public Crew(final String nickname) {
        validateNotNull(nickname);
        this.nickname = nickname;
    }

    private void validateNotNull(final String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("크루는 닉네임을 가지고 있어야 합니다.");
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

        final Crew crew = (Crew) o;
        
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
