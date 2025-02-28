package domain;

public class Nickname {
    private static final String NICKNAME_REGEX = "^[가-힣]{2,4}$";

    private final String value;

    public Nickname(final String nickname) {
        validate(nickname);
        this.value = nickname;
    }

    public String getValue() {
        return value;
    }

    private void validate(final String nickname) {
        if (!nickname.matches(NICKNAME_REGEX)) {
            throw new IllegalArgumentException("닉네임은 2~4글자이며 한글로만 작성이 가능합니다.");
        }
    }
}
