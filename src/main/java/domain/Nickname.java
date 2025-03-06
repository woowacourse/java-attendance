package domain;

public record Nickname(String value) {

    public Nickname {
        validate(value);
    }

    public static Nickname from(String nickname) {
        return new Nickname(nickname);
    }

    private void validate(String nickname) {
        validateLength(nickname);
    }

    private void validateLength(String nickname) {
        if (nickname == null || nickname.length() < 2) {
            throw new IllegalArgumentException("닉네임은 2글자 이상 입력해야 합니다.");
        }
    }
}
