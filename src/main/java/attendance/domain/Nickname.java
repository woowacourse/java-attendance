package attendance.domain;

import attendance.util.ErrorMessage;

public record Nickname(String nickname) {

    public Nickname {
        validateBlankOrNull(nickname);
    }

    private void validateBlankOrNull(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.NICKNAME_MISSING_ERROR.getMessage());
        }
    }
}
