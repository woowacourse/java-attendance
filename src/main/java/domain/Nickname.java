package domain;

public record Nickname(String nickname) {

    public Nickname {
        checkEmptyNickName(nickname);
    }

    private void checkEmptyNickName(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임이 유효하지 않습니다.");
        }
    }
}
