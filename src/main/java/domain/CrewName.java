package domain;

import except.AttendanceException;
import java.util.Objects;

public class CrewName {

    private static final String NAME_CANNOT_BE_EMPTY = "닉네임은 공백일 수 없습니다.";
    private static final String INVALID_NAME_LENGTH = "닉네임은 2-4글자만 허용됩니다.";
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 4;

    private final String nickname;

    public CrewName(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isEmpty() || nickname.isBlank()) {
            throw new AttendanceException(NAME_CANNOT_BE_EMPTY);
        }
        if (nickname.length() < MIN_NAME_LENGTH || nickname.length() > MAX_NAME_LENGTH) {
            throw new AttendanceException(INVALID_NAME_LENGTH);
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
        CrewName crewName = (CrewName) o;
        return Objects.equals(nickname, crewName.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public String nickname() {
        return nickname;
    }
}
