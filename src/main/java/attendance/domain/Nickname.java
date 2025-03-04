package attendance.domain;

import attendance.util.ErrorMessage;

public record Nickname(String nickname) implements Comparable<Nickname> {

    public Nickname {
        validateBlankOrNull(nickname);
    }

    private void validateBlankOrNull(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.NICKNAME_MISSING_ERROR.getMessage());
        }
    }

    @Override
    public int compareTo(Nickname other) {
        return this.nickname.compareTo(other.nickname);
    }
}
