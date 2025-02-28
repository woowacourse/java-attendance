package attendance.domain.crew;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.util.Objects;

public final class Crew {

    private final String nickname;

    public Crew(String nickname) {
        validateBlankNickname(nickname);
        this.nickname = nickname;
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Crew crew = (Crew) object;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    private void validateBlankNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new AttendanceException(ExceptionMessage.BLANK_NICKNAME.getMessage());
        }

    }
}
