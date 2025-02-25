package domain;

public class Nickname {
    private static final String NICKNAME_REGEX = "^[가-힣]{2,4}$";

    private final String displayName;

    public Nickname(final String displayName) {
        validateNickname(displayName);
        this.displayName = displayName;
    }

    private void validateNickname(final String nickname) {
        if (!nickname.matches(NICKNAME_REGEX)) {
            throw new IllegalArgumentException("닉네임은 2~4글자이며 한글로만 작성이 가능합니다.");
        }
    }
}
