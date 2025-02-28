package attendance.domain;

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
}
